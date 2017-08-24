package manager;

import java.sql.Connection;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import manager.util.DBUtil;

@WebListener
public class ContextInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("contextInitialized");
        ServletContext context = event.getServletContext();
        Connection conn = DBUtil.getConnection();
        context.setAttribute("DBConnection", conn);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {}
    
}
