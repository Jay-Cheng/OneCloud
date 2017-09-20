package manager;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import manager.util.HibernateUtil;


@WebListener
public class ContextInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("contextInitialized");
        ServletContext context = event.getServletContext();
        context.setAttribute("file_base", "D:\\Programming\\Java\\onecloud_files");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        HibernateUtil.closeSessionFactory();// 不关闭SessionFactory的话，服务器将无法正常关闭
        System.out.println("contextDestroyed");
    }
    
}
