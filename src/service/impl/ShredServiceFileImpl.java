package service.impl;

import java.time.LocalDateTime;

import org.hibernate.Session;

import com.alibaba.fastjson.JSONObject;

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
    
    private LocalFileDAO localFileDAO = LocalFileDAOFactory.getInstance("hibernate");
    private FileDAO fileDAO = FileDAOFactory.getInstance("hibernate");
    private UserDAO userDAO = UserDAOFactory.getInstance("hibernate");
    
    @Override
    public JSONObject serve(long id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        JSONObject result = new JSONObject();
        
        
        try {
            LocalFileDO localFile = localFileDAO.read(id);
            FileDO file = fileDAO.read(localFile.getFileID());
            UserDO user = userDAO.read(localFile.getUserID());
            localFileDAO.delete(localFile);
            user.setUsedCapacity(user.getUsedCapacity() - file.getSize());
            user.setLdtModified(LocalDateTime.now());
            userDAO.update(user);
            
            result.put("cap", user.getUsedCapacity());
        } catch (Exception e) {
            
            session.getTransaction().rollback();
            result.put("status", 2);
            result.put("msg", "fail");
            return result;
        }
        
        result.put("status", 1);
        result.put("msg", "success");
        
        session.getTransaction().commit();
        return result;
    }
    
}
