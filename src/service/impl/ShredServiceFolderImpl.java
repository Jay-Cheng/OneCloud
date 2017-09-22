package service.impl;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

import org.hibernate.Session;

import dao.FileDAO;
import dao.LocalFileDAO;
import dao.LocalFolderDAO;
import dao.UserDAO;
import dao.entity.FileDO;
import dao.entity.LocalFileDO;
import dao.entity.LocalFolderDO;
import dao.entity.UserDO;
import dao.factory.FileDAOFactory;
import dao.factory.LocalFileDAOFactory;
import dao.factory.LocalFolderDAOFactory;
import dao.factory.UserDAOFactory;
import manager.util.HibernateUtil;
import service.ShredService;

public class ShredServiceFolderImpl implements ShredService {
    
    private static ReentrantLock lock = new ReentrantLock();
    
    private UserDAO userDAO = UserDAOFactory.getInstance("hibernate");
    private FileDAO fileDAO = FileDAOFactory.getInstance("hibernate");
    private LocalFileDAO localFileDAO = LocalFileDAOFactory.getInstance("hibernate");
    private LocalFolderDAO localFolderDAO = LocalFolderDAOFactory.getInstance("hibernate");
    
    @Override
    public boolean serve(long id) {
        lock.lock();
        
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        long userID = localFolderDAO.read(id).getUserID();
        
        Queue<Long> queue = new LinkedList<>();
        queue.add(id);
        try {
            long deleteCap = 0L;// 删除的容量大小
            while (!queue.isEmpty()) {
                Long parent = queue.poll();
                List<LocalFileDO> localFileList = localFileDAO.listByParent(parent);
                for (LocalFileDO localFile :localFileList) {
                    FileDO file = fileDAO.read(localFile.getFileID());
                    deleteCap += file.getSize();
                    localFileDAO.delete(localFile);
                }
                
                List<LocalFolderDO> localFolderList = localFolderDAO.listByParent(parent);
                for (LocalFolderDO localFolder: localFolderList) {
                    queue.add(localFolder.getId());
                }
                localFolderDAO.delete(localFolderDAO.read(parent));
            }
            
            /* 更新用户存储空间 */
            UserDO user = userDAO.read(userID);
            user.setUsedCapacity(user.getUsedCapacity() - deleteCap);
            user.setLdtModified(LocalDateTime.now());
            userDAO.update(user);
            
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        } finally {
            lock.unlock();
        }
    }
}
