package service.impl;

import java.util.List;

import org.hibernate.Session;

import com.alibaba.fastjson.JSONObject;

import dao.LocalFileDAO;
import dao.entity.LocalFileDO;
import dao.factory.LocalFileDAOFactory;
import manager.util.HibernateUtil;
import service.GetFileService;
import service.dto.DTOConvertor;

public class GetFileServiceImpl implements GetFileService {
    
    private LocalFileDAO localFileDAO = LocalFileDAOFactory.getInstance("hibernate");
    
    @Override
    public JSONObject serve(long userID, String type) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        JSONObject result = new JSONObject();

        List<LocalFileDO> files = null;
        switch(type) {
        case "lately":files = localFileDAO.listRecentFile(userID);break;
        case "document":files = localFileDAO.listDocument(userID);break;
        case "picture":files = localFileDAO.listPicture(userID);break;
        case "video":files = localFileDAO.listVideo(userID);break;
        case "music":files = localFileDAO.listMusic(userID);break;
        }
        if (files == null) {
            throw new IllegalArgumentException();
        }
        
        result.put("files", DTOConvertor.convertFileList(files));
        
        session.getTransaction().commit();
        return result;
    }
    
    
}
