package com.zhengzijie.onecloud.service.impl;

import java.time.LocalDateTime;

import org.hibernate.Session;

import com.alibaba.fastjson.JSONObject;
import com.zhengzijie.onecloud.dao.LocalFileDAO;
import com.zhengzijie.onecloud.dao.entity.LocalFileDO;
import com.zhengzijie.onecloud.dao.factory.LocalFileDAOFactory;
import com.zhengzijie.onecloud.manager.util.HibernateUtil;
import com.zhengzijie.onecloud.service.MoveService;

public class MoveServiceFileImpl implements MoveService {
    
    private LocalFileDAO localFileDAO = LocalFileDAOFactory.getInstance("hibernate");
     
    @Override
    public JSONObject serve(Long id, Long moveTo) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        JSONObject result = new JSONObject();
        
        LocalFileDO file = localFileDAO.read(id);
        file.setParent(moveTo);
        file.setLdtModified(LocalDateTime.now());
        try {
            localFileDAO.update(file);
        } catch (Exception e) {
            session.getTransaction().rollback();
            result.put("status", 2);
            result.put("msg", "fail");
            return result;
        }
        
        result.put("status", 1);
        result.put("msg", "success");
        result.put("ldtModified", file.getLdtModified());
        
        session.getTransaction().commit();
        return result;
    }

}
