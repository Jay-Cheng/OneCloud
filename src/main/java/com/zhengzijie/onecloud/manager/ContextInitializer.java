package com.zhengzijie.onecloud.manager;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.zhengzijie.onecloud.manager.util.HibernateUtil;


@WebListener
public class ContextInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("contextInitialized");
        ServletContext context = event.getServletContext();
        context.setInitParameter("filebase", "/home/jay/Programming/Java/apache-tomcat-8.5.23/webapps/onecloud_files");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        HibernateUtil.closeSessionFactory();// 不关闭SessionFactory的话，服务器将无法正常关闭
        System.out.println("contextDestroyed");
    }
    
}
