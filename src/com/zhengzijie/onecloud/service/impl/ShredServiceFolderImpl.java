package com.zhengzijie.onecloud.service.impl;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

import org.hibernate.Session;

import com.zhengzijie.onecloud.dao.FileDAO;
import com.zhengzijie.onecloud.dao.LocalFileDAO;
import com.zhengzijie.onecloud.dao.LocalFolderDAO;
import com.zhengzijie.onecloud.dao.UserDAO;
import com.zhengzijie.onecloud.dao.entity.FileDO;
import com.zhengzijie.onecloud.dao.entity.LocalFileDO;
import com.zhengzijie.onecloud.dao.entity.LocalFolderDO;
import com.zhengzijie.onecloud.dao.entity.UserDO;
import com.zhengzijie.onecloud.dao.factory.FileDAOFactory;
import com.zhengzijie.onecloud.dao.factory.LocalFileDAOFactory;
import com.zhengzijie.onecloud.dao.factory.LocalFolderDAOFactory;
import com.zhengzijie.onecloud.dao.factory.UserDAOFactory;
import com.zhengzijie.onecloud.manager.util.HibernateUtil;
import com.zhengzijie.onecloud.service.ShredService;

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
