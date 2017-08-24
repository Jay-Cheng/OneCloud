package manager.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;
    private static Session session;
    
    static {
        registry = new StandardServiceRegistryBuilder().configure().build();
        sessionFactory = new MetadataSources().buildMetadata(registry).buildSessionFactory();
    }
    
    public static Session getSession() {
        if (session == null) {
            session = sessionFactory.getCurrentSession();
        }
        return session;
    }
}
