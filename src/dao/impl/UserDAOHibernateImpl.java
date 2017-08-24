package dao.impl;

import java.util.Map;
import java.util.Map.Entry;

import dao.UserDAO;
import dao.entity.UserDO;

public class UserDAOHibernateImpl implements UserDAO {

    @Override
    public UserDO get(Map<String, Object> params) {
        StringBuilder hql = new StringBuilder();
        hql.append("from UserDO where ");
        for (Entry<String, Object> entry: params.entrySet()) {
            hql.append(entry.getKey() + "= ? and ");
        }
        return null;
    }

    @Override
    public UserDO list(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean save(UserDO t) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean remove(UserDO t) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean update(UserDO t) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
