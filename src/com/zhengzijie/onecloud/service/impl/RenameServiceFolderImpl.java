package com.zhengzijie.onecloud.service.impl;

import java.time.LocalDateTime;

import org.hibernate.Session;

import com.alibaba.fastjson.JSONObject;
import com.zhengzijie.onecloud.dao.LocalFolderDAO;
import com.zhengzijie.onecloud.dao.entity.LocalFolderDO;
import com.zhengzijie.onecloud.dao.factory.LocalFolderDAOFactory;
import com.zhengzijie.onecloud.manager.util.HibernateUtil;
import com.zhengzijie.onecloud.service.RenameService;

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
