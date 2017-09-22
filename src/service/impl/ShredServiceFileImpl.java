package service.impl;

import java.time.LocalDateTime;
import java.util.concurrent.locks.ReentrantLock;

import org.hibernate.Session;

import dao.FileDAO;
import dao.LocalFileDAO;
import dao.UserDAO;
import dao.entity.FileDO;
import dao.entity.LocalFileDO;
import dao.entity.UserDO;
import dao.factory.FileDAOFactory;
import dao.factory.LocalFileDAOFactory;
import dao.factory.UserDAOFactory;
import manager.util.HibernateUtil;
import service.ShredService;

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
