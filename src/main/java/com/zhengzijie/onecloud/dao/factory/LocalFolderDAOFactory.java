package com.zhengzijie.onecloud.dao.factory;

import com.zhengzijie.onecloud.dao.LocalFolderDAO;
import com.zhengzijie.onecloud.dao.impl.hibernate.LocalFolderDAOHibernateImpl;

public class LocalFolderDAOFactory {
    
    private static final LocalFolderDAOHibernateImpl hibernateImpl = new LocalFolderDAOHibernateImpl();
    
    public static LocalFolderDAO getInstance(String type) {
        if ("hibernate".equalsIgnoreCase(type)) {
            return hibernateImpl;
        }
        return null;
    }
}
