package dao.factory;

import dao.LocalFolderDAO;
import dao.impl.LocalFolderDAOHibernateImpl;

public class LocalFolderDAOFactory {
    public LocalFolderDAO getLocalFolderDAO(String type) {
        if ("Hibernate".equals(type)) {
            return new LocalFolderDAOHibernateImpl();
        }
        return null;
    }
}
