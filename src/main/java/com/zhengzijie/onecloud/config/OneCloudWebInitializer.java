package com.zhengzijie.onecloud.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


public class OneCloudWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

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

}
