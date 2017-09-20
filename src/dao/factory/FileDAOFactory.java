package dao.factory;

import dao.FileDAO;
import dao.impl.hibernate.FileDAOHibernateImpl;

public class FileDAOFactory {
    
    private static final FileDAOHibernateImpl hibernateImpl = new FileDAOHibernateImpl();
    
    public static FileDAO getInstance(String type) {
        if ("hibernate".equalsIgnoreCase(type)) {
            return hibernateImpl;
        }
        return null;
    }
    
}
