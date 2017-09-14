package dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

import manager.exception.DBQueryException;
import manager.util.HibernateUtil;

public interface GenericDAO<T extends Object> {
    
    default T get(Map<String, Object> params) throws DBQueryException {
        List<T> result = list(params);
        
        if (result.isEmpty()) {
            return null;
        } else if (result.size() == 1) {
            return result.get(0);
        } else {
            throw new DBQueryException();
        }
    }
    default void save(T obj) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        
        session.persist(obj);
        
        t.commit();
        session.close();
    }
    
    List<T> list(Map<String, Object> params);
    boolean remove(T t);
    
}
