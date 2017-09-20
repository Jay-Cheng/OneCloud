package service.impl;

import java.time.LocalDateTime;

import org.hibernate.Session;

import com.alibaba.fastjson.JSONObject;

import dao.LocalFolderDAO;
import dao.entity.LocalFolderDO;
import dao.factory.LocalFolderDAOFactory;
import manager.util.HibernateUtil;
import service.AddFolderService;

public class AddFolderServiceImpl implements AddFolderService {
    
    private LocalFolderDAO dao = LocalFolderDAOFactory.getInstance("hibernate");
    
    @Override
    public JSONObject serve(LocalFolderDO folder) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        JSONObject result = new JSONObject();
        
        folder.setLdtCreate(LocalDateTime.now());
        folder.setLdtModified(folder.getLdtCreate());
        try {
            dao.create(folder);
        } catch (Exception e) {
            session.getTransaction().rollback();
            result.put("status", 2);
            result.put("msg", "fail");
            return result;
        }
        
        result.put("status", 1);
        result.put("msg", "success");
        result.put("folderID", folder.getId());
        result.put("ldtModified", folder.getLdtModified());
        
        session.getTransaction().commit();
        return result;
    }

}
