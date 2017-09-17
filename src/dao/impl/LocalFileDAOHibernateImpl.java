package dao.impl;

import java.time.LocalDateTime;
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

import dao.LocalFileDAO;
import dao.entity.LocalFileDO;
import manager.util.HibernateUtil;

public class LocalFileDAOHibernateImpl implements LocalFileDAO {

    @Override
    public List<LocalFileDO> list(Map<String, Object> params) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<LocalFileDO> cq = cb.createQuery(LocalFileDO.class);
        Root<LocalFileDO> root = cq.from(LocalFileDO.class);
        List<Predicate> predicateList = new ArrayList<>();
        for (Entry<String, Object> entry : params.entrySet()) {
            Predicate condition = cb.equal(root.get(entry.getKey()), entry.getValue());
            predicateList.add(condition);
        }
        cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
        TypedQuery<LocalFileDO> query = session.createQuery(cq);
        List<LocalFileDO> result = query.getResultList();
        
        t.commit();
        session.close();
        
        return result;
    }

    @Override
    public boolean remove(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        
        try {
            LocalFileDO file = session.load(LocalFileDO.class, id);
            session.delete(file);
            t.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            t.rollback();
            return false;
        } finally {
            session.close();
        }
    }
    
    public LocalDateTime rename(LocalFileDO newDO) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        
        
        LocalFileDO oldDO = session.get(LocalFileDO.class, newDO.getId());
        
        LocalDateTime ldtModified = LocalDateTime.now();
        oldDO.setLdtModified(ldtModified);
        oldDO.setLocalName(newDO.getLocalName());
        oldDO.setLocalType(newDO.getLocalType());
        
        
        t.commit();
        session.close();
        return ldtModified;
    }

    @Override
    public LocalDateTime move(Long id, Long to) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        
        LocalFileDO oldDO = session.get(LocalFileDO.class, id);
        
        LocalDateTime ldtModified = LocalDateTime.now();
        oldDO.setLdtModified(ldtModified);
        oldDO.setParent(to);
        
        t.commit();
        session.close();
        return ldtModified;
    }
    
}
