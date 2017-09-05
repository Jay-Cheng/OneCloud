package dao.factory;

import dao.FileDAO;
import dao.impl.FileDAOHibernateImpl;

public class FileDAOFactory {
    public FileDAO getFileDAO(String type) {
        if ("Hibernate".equals(type)) {
            return new FileDAOHibernateImpl();
        }
        return null;
    }
}
