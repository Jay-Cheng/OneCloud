package com.zhengzijie.onecloud.config;


import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.zhengzijie.onecloud.Marker4ComponentScan;
import com.zhengzijie.onecloud.web.interceptor.AuthenticationInterceptor;

@Configuration
//@ComponentScan("com.zhengzijie.onecloud.web")
@ComponentScan(basePackageClasses = { Marker4ComponentScan.class })// 扫描web组件
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    
    @Bean
    public ViewResolver viewResolver() {
      InternalResourceViewResolver resolver = new InternalResourceViewResolver();
      resolver.setPrefix("/WEB-INF/views/");
      resolver.setSuffix(".html");
      return resolver;
    }
    
    /**
     * 对静态资源的请求转发到Servlet容器中默认的Servlet上，而不使用DispatcherServlet处理该类请求
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();  
        FastJsonConfig fastJsonConfig = new FastJsonConfig();  
        fastJsonConfig.setSerializerFeatures(  
                SerializerFeature.PrettyFormat  
        );  
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);  
        converters.add(fastJsonHttpMessageConverter);  
    }

    @Bean 
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor()).addPathPatterns("/**");
    }
    
    
}





