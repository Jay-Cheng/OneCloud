package service.impl;

import java.time.LocalDateTime;

import org.hibernate.Session;

import com.alibaba.fastjson.JSONObject;

import dao.LocalFolderDAO;
import dao.entity.LocalFolderDO;
import dao.factory.LocalFolderDAOFactory;
import manager.util.HibernateUtil;
import service.RenameService;

public class RenameServiceFolderImpl implements RenameService {

    private LocalFolderDAO localFolderDAO = LocalFolderDAOFactory.getInstance("hibernate");
    
    @Override
    public JSONObject serve(long id, String localName, String localType) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        JSONObject result = new JSONObject();
        
        try {
            LocalFolderDO folder = localFolderDAO.read(id);
            folder.setLocalName(localName);
            folder.setLdtModified(LocalDateTime.now());
            localFolderDAO.update(folder);
            result.put("ldtModified", folder.getLdtModified());
        } catch(Exception e) {
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
