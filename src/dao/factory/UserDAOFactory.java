package dao.factory;

import dao.UserDAO;
import dao.impl.hibernate.UserDAOHibernateImpl;

public class UserDAOFactory {
    
    private static final UserDAOHibernateImpl hibernateImpl = new UserDAOHibernateImpl();
    
    public static UserDAO getInstance(String type) {
        if ("hibernate".equalsIgnoreCase(type)) {
            return hibernateImpl;
        }
        return null;
    }
}
