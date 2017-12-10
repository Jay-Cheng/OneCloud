package com.zhengzijie.onecloud.dao.impl.hibernate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.zhengzijie.onecloud.dao.LocalFileDAO;
import com.zhengzijie.onecloud.dao.entity.LocalFileDO;

@Repository
public class LocalFileDAOHibernateImpl extends GenericDAOHibernateImpl<LocalFileDO> implements LocalFileDAO {

    public LocalFileDAOHibernateImpl() {
        super(LocalFileDO.class);
    }

    @Override
    public LocalFileDO getByPath(Long userID, Long parent, String localName, String localType) {
        Session session = sessionFactory.getCurrentSession();
        @SuppressWarnings("unchecked")
        Query<LocalFileDO> query = session.createQuery("from LocalFileDO file where file.userID = :userID and file.parent = :parent and file.localName = :localName and file.localType = :localType");
        query.setParameter("userID", userID);
        query.setParameter("parent", parent);
        query.setParameter("localName", localName);
        query.setParameter("localType", localType);
        List<LocalFileDO> result = query.list();
        if (result.size() == 1) {
            return result.get(0);
        } else if (result.isEmpty()) {
            return null;
        } else {
            throw new IllegalStateException("multiple result");
        }
    }

    @Override
    public List<LocalFileDO> listByParent(Long parent) {
        Session session = sessionFactory.getCurrentSession();
        @SuppressWarnings("unchecked")
        Query<LocalFileDO> query = session.createQuery("from LocalFileDO file where file.parent = :parent");
        query.setParameter("parent", parent);
        List<LocalFileDO> result = query.list();
        return result;
    }
    
    @Override
    public List<LocalFileDO> listRootContents(Long parent, Long userID) {
        Session session = sessionFactory.getCurrentSession();
        @SuppressWarnings("unchecked")
        Query<LocalFileDO> query = session.createQuery("from LocalFileDO file where file.parent = :parent and file.userID = :userID");
        query.setParameter("parent", parent);
        query.setParameter("userID", userID);
        List<LocalFileDO> result = query.list();
        return result;
    }
    
    @Override
    public List<LocalFileDO> listRecentFile(Long userID) {
        Session session = sessionFactory.getCurrentSession();
        LocalDateTime aWeekAgo = LocalDateTime.now().minusDays(7);
        @SuppressWarnings("unchecked")
        Query<LocalFileDO> query = session.createQuery("from LocalFileDO file where file.parent!=2 and file.parent!=3 and file.userID=:userID and file.ldtCreate > :aWeekAgo");
        query.setParameter("userID", userID);
        query.setParameter("aWeekAgo", aWeekAgo);
        List<LocalFileDO> result = query.list();
        return result;
    }

    @Override @Deprecated
    public List<LocalFileDO> listDocument(long userID) {
        Session session = sessionFactory.getCurrentSession();
        @SuppressWarnings("unchecked")
        Query<LocalFileDO> query = session.createQuery("from LocalFileDO file where file.parent!=2 and file.parent!=3 and file.userID=:userID and (file.localType='doc' or file.localType='xls' or file.localType='ppt' or file.localType='txt')");
        query.setParameter("userID", userID);
        List<LocalFileDO> result = query.list();
        return result;
    }

    @Override @Deprecated
    public List<LocalFileDO> listPhoto(long userID) {
        Session session = sessionFactory.getCurrentSession();
        @SuppressWarnings("unchecked")
        Query<LocalFileDO> query = session.createQuery("from LocalFileDO file where file.parent!=2 and file.parent!=3 and file.userID=:userID and (file.localType='jpeg' or file.localType='png' or file.localType='gif' or file.localType='jpg')");
        query.setParameter("userID", userID);
        List<LocalFileDO> result = query.list();
        return result;
    }

    @Override @Deprecated
    public List<LocalFileDO> listVideo(long userID) {
        Session session = sessionFactory.getCurrentSession();
        @SuppressWarnings("unchecked")
        Query<LocalFileDO> query = session.createQuery("from LocalFileDO file where file.parent!=2 and file.parent!=3 and file.userID=:userID and file.localType='mp4'");
        query.setParameter("userID", userID);
        List<LocalFileDO> result = query.list();
        return result;
    }

    @Override @Deprecated
    public List<LocalFileDO> listAudio(long userID) {
        Session session = sessionFactory.getCurrentSession();
        @SuppressWarnings("unchecked")
        Query<LocalFileDO> query = session.createQuery("from LocalFileDO file where file.parent!=2 and file.parent!=3 and file.userID=:userID and file.localType='mp3'");
        query.setParameter("userID", userID);
        List<LocalFileDO> result = query.list();
        return result;
    }

    @Override
    public List<LocalFileDO> listByName(Long userID, String name) {
        Session session = sessionFactory.getCurrentSession();
        @SuppressWarnings("unchecked")
        Query<LocalFileDO> query = session.createQuery("from LocalFileDO file where file.parent!=2 and file.parent!=3 and file.userID=:userID and concat(file.localName, '.', file.localType) like :name");
        query.setParameter("userID", userID);
        query.setParameter("name", "%" + name + "%");
        List<LocalFileDO> result = query.list();
        return result;
    }

    @Override
    public List<LocalFileDO> listByLocalType(Long userID, String[] localTypes) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<LocalFileDO> cq = cb.createQuery(LocalFileDO.class);
        Root<LocalFileDO> root = cq.from(LocalFileDO.class);
        
        Predicate equalUserIDCondition = cb.equal(root.get("userID"), userID);
        Predicate notInSafeboxCondition = cb.notEqual(root.get("parent"), 2);
        Predicate notInRecycleCondition = cb.notEqual(root.get("parent"), 3);
        
        List<Predicate> equalLocalTypeConditionList = new ArrayList<>();
        for (String type : localTypes) {
            Predicate condition = cb.equal(root.get("localType"), type);
            equalLocalTypeConditionList.add(condition);
        }
        Predicate MatchOneLocalTypeCondition = cb.or(equalLocalTypeConditionList.toArray(new Predicate[equalLocalTypeConditionList.size()]));
        
        Predicate[] conditionArray = {equalUserIDCondition, notInSafeboxCondition, notInRecycleCondition
                , MatchOneLocalTypeCondition};
        cq.where(conditionArray);
        TypedQuery<LocalFileDO> query = session.createQuery(cq);
        List<LocalFileDO> result = query.getResultList();
        
        return result;
    }

}
