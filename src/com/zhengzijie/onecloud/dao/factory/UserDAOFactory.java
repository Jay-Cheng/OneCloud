package com.zhengzijie.onecloud.dao.factory;

import com.zhengzijie.onecloud.dao.UserDAO;
import com.zhengzijie.onecloud.dao.impl.hibernate.UserDAOHibernateImpl;

public class UserDAOFactory {
    
    private static final UserDAOHibernateImpl hibernateImpl = new UserDAOHibernateImpl();
    
    public static UserDAO getInstance(String type) {
        if ("hibernate".equalsIgnoreCase(type)) {
            return hibernateImpl;
        }
        return null;
    }
}
