package dao.factory;

import dao.LocalFileDAO;
import dao.impl.LocalFileDAOHibernateImpl;

public class LocalFileDAOFactory {
    public LocalFileDAO getLocalFileDAO(String type) {
        if ("Hibernate".equals(type)) {
            return new LocalFileDAOHibernateImpl();
        }
        return null;
    }
}
