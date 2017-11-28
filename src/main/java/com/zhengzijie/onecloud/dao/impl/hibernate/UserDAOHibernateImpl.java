package com.zhengzijie.onecloud.dao.impl.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.zhengzijie.onecloud.dao.UserDAO;
import com.zhengzijie.onecloud.dao.entity.UserDO;

@Repository
public class UserDAOHibernateImpl extends GenericDAOHibernateImpl<UserDO> implements UserDAO {

    public UserDAOHibernateImpl() {
        super(UserDO.class);
    }

    @Override
    public UserDO getByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        @SuppressWarnings("unchecked")
        Query<UserDO> query = session.createQuery("from UserDO user where user.username = :username");
        query.setParameter("username", username);
        List<UserDO> list = query.list();
        if (list.isEmpty()) {
            return null;
        } else if (list.size() == 1) {
            return list.get(0);
        } else {
            throw new IllegalStateException("multiple result for username=[" + username + "]");
        }

    }
    
}
