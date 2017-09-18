package dao.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import dao.LocalFolderDAO;
import dao.entity.FileDO;
import dao.entity.LocalFileDO;
import dao.entity.LocalFolderDO;
import dao.entity.UserDO;
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
    /**
     * 删除文件夹下的所有子文件夹及子文件记录
     * @param 需要删除文件夹的id
     * @return true if remove successfully, false otherwise;
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public boolean remove(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        
        Queue<Long> queue = new LinkedList<>();
        queue.add(id);
        long cap = 0L;
        try {
            LocalFolderDO localFolder = session.load(LocalFolderDO.class, id);
            UserDO user = session.load(UserDO.class, localFolder.getUserID());
            while (!queue.isEmpty()) {
                Long parent = queue.poll();
                Query fileQuery = session.createQuery("from LocalFileDO file where file.parent=?");
                fileQuery.setParameter(0, parent);
                List<LocalFileDO> localFileList = fileQuery.list();
                for (LocalFileDO localFile :localFileList) {
                    FileDO file = session.load(FileDO.class, localFile.getFileID());
                    cap += file.getSize();
                    session.delete(localFile);
                }
                
                Query folderQuery = session.createQuery("select folder.id from LocalFolderDO folder where folder.parent=?");
                folderQuery.setParameter(0, parent);
                List<Long> folderList = folderQuery.list();
                
                for (Long subFolderID : folderList) {
                    queue.add(subFolderID);
                }
                
                session.delete(session.load(LocalFolderDO.class, parent));
            }
            user.setUsedCapacity(user.getUsedCapacity() - cap);
            user.setLdtModified(LocalDateTime.now());
            session.update(user);
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
