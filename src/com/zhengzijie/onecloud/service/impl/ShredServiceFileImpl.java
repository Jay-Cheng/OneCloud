package com.zhengzijie.onecloud.service.impl;

import java.time.LocalDateTime;
import java.util.concurrent.locks.ReentrantLock;

import org.hibernate.Session;

import com.zhengzijie.onecloud.dao.FileDAO;
import com.zhengzijie.onecloud.dao.LocalFileDAO;
import com.zhengzijie.onecloud.dao.UserDAO;
import com.zhengzijie.onecloud.dao.entity.FileDO;
import com.zhengzijie.onecloud.dao.entity.LocalFileDO;
import com.zhengzijie.onecloud.dao.entity.UserDO;
import com.zhengzijie.onecloud.dao.factory.FileDAOFactory;
import com.zhengzijie.onecloud.dao.factory.LocalFileDAOFactory;
import com.zhengzijie.onecloud.dao.factory.UserDAOFactory;
import com.zhengzijie.onecloud.manager.util.HibernateUtil;
import com.zhengzijie.onecloud.service.ShredService;

public class ShredServiceFileImpl implements ShredService {
    
    private static ReentrantLock lock = new ReentrantLock(); 
    
    private LocalFileDAO localFileDAO = LocalFileDAOFactory.getInstance("hibernate");
    private FileDAO fileDAO = FileDAOFactory.getInstance("hibernate");
    private UserDAO userDAO = UserDAOFactory.getInstance("hibernate");
    
    @Override
    public boolean serve(long id) {
        lock.lock();
        
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            
            LocalFileDO localFile = localFileDAO.read(id);
            localFileDAO.delete(localFile);
            FileDO file = fileDAO.read(localFile.getFileID());
            UserDO user = userDAO.read(localFile.getUserID());
            user.setUsedCapacity(user.getUsedCapacity() - file.getSize());
            user.setLdtModified(LocalDateTime.now());
            userDAO.update(user);
            
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        } finally {
            lock.unlock();
        }
    }
}
