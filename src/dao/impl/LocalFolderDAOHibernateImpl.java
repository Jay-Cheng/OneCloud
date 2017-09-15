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

import dao.LocalFolderDAO;
import dao.entity.LocalFolderDO;
import manager.util.HibernateUtil;

public class LocalFolderDAOHibernateImpl implements LocalFolderDAO {

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
    public boolean remove(LocalFolderDO t) {
        // TODO Auto-generated method stub
        return false;
    }
    
    public LocalDateTime rename(LocalFolderDO newDO) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
             
        
        LocalFolderDO oldDO = session.get(LocalFolderDO.class, newDO.getId());
        
        LocalDateTime ldtModified = LocalDateTime.now();
        oldDO.setLdtModified(ldtModified);
        oldDO.setLocalName(newDO.getLocalName());
        
        
        t.commit();
        session.close();
        return ldtModified;
    }

    @Override
    public LocalDateTime move(Long id, Long to) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        
        LocalFolderDO oldDO = session.get(LocalFolderDO.class, id);
        
        LocalDateTime ldtModified = LocalDateTime.now();
        oldDO.setLdtModified(ldtModified);
        oldDO.setParent(to);
        
        t.commit();
        session.close();
        return ldtModified;
    }
}
