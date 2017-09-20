package service.impl;


import java.time.LocalDateTime;

import org.hibernate.Session;

import com.alibaba.fastjson.JSONObject;

import dao.LocalFileDAO;
import dao.entity.LocalFileDO;
import dao.factory.LocalFileDAOFactory;
import manager.util.HibernateUtil;
import service.RenameService;

public class RenameServiceFileImpl implements RenameService {
    
    private LocalFileDAO localFileDAO = LocalFileDAOFactory.getInstance("hibernate");

    @Override
    public JSONObject serve(long id, String localName, String localType) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        JSONObject result = new JSONObject();
        
        try {
            LocalFileDO file = localFileDAO.read(id);
            file.setLocalName(localName);
            file.setLocalType(localType);
            file.setLdtModified(LocalDateTime.now());
            localFileDAO.update(file);
            result.put("ldtModified", file.getLdtModified());
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
