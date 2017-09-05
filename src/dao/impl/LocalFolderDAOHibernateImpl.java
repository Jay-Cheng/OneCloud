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

import dao.LocalFolderDAO;
import dao.entity.LocalFolderDO;
import manager.exception.DBQueryException;
import manager.util.HibernateUtil;

public class LocalFolderDAOHibernateImpl implements LocalFolderDAO {

    @Override
    public LocalFolderDO get(Map<String, Object> params) throws DBQueryException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<LocalFolderDO> list(Map<String, Object> params) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<LocalFolderDO> cq = cb.createQuery(LocalFolderDO.class);
        Root<LocalFolderDO> root = cq.from(LocalFolderDO.class);
        List<Predicate> predicateList = new ArrayList<>();
        for (Entry<String, Object> entry : params.entrySet()) {
            Predicate condition = cb.equal(root.get(entry.getKey()), entry.getValue());
            predicateList.add(condition);
        }
        cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
        TypedQuery<LocalFolderDO> query = session.createQuery(cq);
        List<LocalFolderDO> result = query.getResultList();
        
        t.commit();
        session.close();
        
        return result;
    }

    @Override
    public boolean save(LocalFolderDO t) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean remove(LocalFolderDO t) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean update(LocalFolderDO t) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
