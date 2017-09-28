package service.impl;

import java.util.List;

import org.hibernate.Session;

import com.alibaba.fastjson.JSONObject;

import dao.LocalFileDAO;
import dao.entity.LocalFileDO;
import dao.factory.LocalFileDAOFactory;
import manager.util.HibernateUtil;
import service.GetFileService;
import service.SortService;
import service.dto.DTOConvertor;
import service.dto.LocalFileDTO;
import service.factory.SortServiceFactory;

public class GetFileServiceImpl implements GetFileService {
    
    private LocalFileDAO localFileDAO = LocalFileDAOFactory.getInstance("hibernate");
    
    @Override
    public JSONObject serve(long userID, String type) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        JSONObject result = new JSONObject();

        List<LocalFileDO> files = null;
        int sortType = 0;// 默认按字母排序
        switch(type) {
        case "lately":files = localFileDAO.listRecentFile(userID);sortType = 1;break;// 按时间排序
        case "document":files = localFileDAO.listDocument(userID);break;
        case "picture":files = localFileDAO.listPicture(userID);break;
        case "video":files = localFileDAO.listVideo(userID);break;
        case "music":files = localFileDAO.listMusic(userID);break;
        }
        if (files == null) {
            throw new IllegalArgumentException();
        }
        
        List<LocalFileDTO> fileDTOList = DTOConvertor.convertFileList(files);
        LocalFileDTO[] fileDTOArray = fileDTOList.toArray(new LocalFileDTO[fileDTOList.size()]);
        SortService sorter = SortServiceFactory.getService(sortType);
        sorter.serve(fileDTOArray);
        result.put("files", fileDTOArray);
        
        session.getTransaction().commit();
        return result;
    }
    
    
}
