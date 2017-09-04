package dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.UserDAO;
import dao.entity.UserDO;
import manager.exception.DBQueryException;
import manager.util.HibernateUtil;

public class UserDAOHibernateImpl implements UserDAO {

    @Override
    public UserDO get(Map<String, Object> params) throws DBQueryException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UserDO> cq = cb.createQuery(UserDO.class);
        Root<UserDO> root = cq.from(UserDO.class);
        List<Predicate> predicateList = new ArrayList<>();
        for (Entry<String, Object> entry : params.entrySet()) {
            Predicate condition = cb.equal(root.get(entry.getKey()), entry.getValue());
            predicateList.add(condition);
        }
        cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
        TypedQuery<UserDO> query = session.createQuery(cq);
        List<UserDO> result = query.getResultList();
        
        t.commit();
        session.close();
        
        if (result.isEmpty()) {
            return null;
        } else if (result.size() == 1) {
            return result.get(0);
        } else {
            throw new DBQueryException();
        }
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
