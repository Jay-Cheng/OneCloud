package dao.factory;

import dao.UserDAO;
import dao.impl.UserDAOHibernateImpl;

public class UserDAOFactory {
    public UserDAO getUserDAO(String type) {
        if ("Hibernate".equals(type)) {
            return new UserDAOHibernateImpl();
        }
        return null;
    }
}
