package dao.factory;

import dao.LocalFolderDAO;
import dao.impl.hibernate.LocalFolderDAOHibernateImpl;

public class LocalFolderDAOFactory {
    
    private static final LocalFolderDAOHibernateImpl hibernateImpl = new LocalFolderDAOHibernateImpl();
    
    public static LocalFolderDAO getInstance(String type) {
        if ("hibernate".equalsIgnoreCase(type)) {
            return hibernateImpl;
        }
        return null;
    }
}
