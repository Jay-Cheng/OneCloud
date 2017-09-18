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

import dao.UserDAO;
import dao.entity.UserDO;
import manager.util.HibernateUtil;

public class UserDAOHibernateImpl implements UserDAO {

    @Override
    public List<UserDO> list(Map<String, Object> params) {
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
        return result;
    }

    @Override
    public boolean remove(long id) {
        return false;
    }
    
    /**
     * 更新用户的容量
     * @param id 用户id
     * @param size 文件的大小，删除的话size为负
     */
    public void updateCap(long id, int size) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction(); 
        
        UserDO user = session.load(UserDO.class, id);
        user.setUsedCapacity(user.getUsedCapacity() + size);
        user.setLdtModified(LocalDateTime.now());
        session.update(user);
        
        t.commit();
        session.close();
    }
}
