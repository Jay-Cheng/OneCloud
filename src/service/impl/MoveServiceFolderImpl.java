package service.impl;

import java.time.LocalDateTime;

import org.hibernate.Session;

import com.alibaba.fastjson.JSONObject;

import dao.LocalFolderDAO;
import dao.entity.LocalFolderDO;
import dao.factory.LocalFolderDAOFactory;
import manager.util.HibernateUtil;
import service.MoveService;

public class MoveServiceFolderImpl implements MoveService {
    
    private LocalFolderDAO localFolderDAO = LocalFolderDAOFactory.getInstance("hibernate");
    
    @Override
    public JSONObject serve(Long id, Long moveTo) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        JSONObject result = new JSONObject();
        
        LocalFolderDO folder = localFolderDAO.read(id);
        folder.setParent(moveTo);
        folder.setLdtModified(LocalDateTime.now());
        try {
            localFolderDAO.update(folder);
        } catch (Exception e) {
            session.getTransaction().rollback();
            result.put("status", 2);
            result.put("msg", "fail");
            return result;
        }
        
        result.put("status", 1);
        result.put("msg", "success");
        result.put("ldtModified", folder.getLdtModified());
        
        
        session.getTransaction().commit();
        return result;
    }

}
