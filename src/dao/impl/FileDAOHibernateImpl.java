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

import dao.FileDAO;
import dao.entity.FileDO;
import manager.exception.DBQueryException;
import manager.util.HibernateUtil;

public class FileDAOHibernateImpl implements FileDAO {

    @Override
    public FileDO get(Map<String, Object> params) throws DBQueryException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<FileDO> cq = cb.createQuery(FileDO.class);
        Root<FileDO> root = cq.from(FileDO.class);
        List<Predicate> predicateList = new ArrayList<>();
        for (Entry<String, Object> entry : params.entrySet()) {
            Predicate condition = cb.equal(root.get(entry.getKey()), entry.getValue());
            predicateList.add(condition);
        }
        cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
        TypedQuery<FileDO> query = session.createQuery(cq);
        List<FileDO> result = query.getResultList();
        
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
    public List<FileDO> list(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean save(FileDO t) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean remove(FileDO t) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean update(FileDO t) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
