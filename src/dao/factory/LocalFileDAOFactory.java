package dao.factory;

import dao.LocalFileDAO;
import dao.impl.hibernate.LocalFileDAOHibernateImpl;

public class LocalFileDAOFactory {
    
    private static final LocalFileDAOHibernateImpl hibernateImpl = new LocalFileDAOHibernateImpl();
    
    public static LocalFileDAO getInstance(String type) {
        if ("hibernate".equalsIgnoreCase(type)) {
            return hibernateImpl;
        }
        return null;
    }
}
