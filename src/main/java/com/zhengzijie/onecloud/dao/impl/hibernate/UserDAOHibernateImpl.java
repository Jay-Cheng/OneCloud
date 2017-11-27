package com.zhengzijie.onecloud.dao.impl.hibernate;

import org.springframework.stereotype.Repository;

import com.zhengzijie.onecloud.dao.UserDAO;
import com.zhengzijie.onecloud.dao.entity.UserDO;

@Repository
public class UserDAOHibernateImpl extends GenericDAOHibernateImpl<UserDO> implements UserDAO {

    public UserDAOHibernateImpl() {
        super(UserDO.class);
    }
    
}
