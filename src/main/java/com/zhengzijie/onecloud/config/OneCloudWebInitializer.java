package com.zhengzijie.onecloud.config;

import java.nio.charset.StandardCharsets;

import javax.servlet.FilterRegistration;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


public class OneCloudWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected void customizeRegistration(Dynamic registration) {
        registration.setMultipartConfig(new MultipartConfigElement("tmp"));
    }

    /**
     * 配置data repositories & business services 
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        System.out.println("root context initialized");
        return new Class<?>[] { RootConfig.class };
    }
    
    /**
     * 配置Web组件，如Controller，ViewResolver，HandlerMapping
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        System.out.println("servlet context initialized");
        return new Class<?>[] { WebConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
    
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        System.out.println("startup");
        FilterRegistration.Dynamic characterEncodingFilter = 
                servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
        characterEncodingFilter.setInitParameter("encoding", String.valueOf(StandardCharsets.UTF_8));
        characterEncodingFilter.setInitParameter("forceEncoding", "true");
        characterEncodingFilter.addMappingForUrlPatterns(null, false, "/*");
        super.onStartup(servletContext);
    }
}
