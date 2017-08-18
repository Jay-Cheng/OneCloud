package service;

import java.sql.Connection;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import util.DBUtil;

public class ContextInitializer implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        Connection conn = DBUtil.getConnection();
        context.setAttribute("DBConnection", conn);
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {}
    
}
