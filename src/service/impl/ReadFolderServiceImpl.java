package service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.alibaba.fastjson.JSONObject;

import dao.LocalFileDAO;
import dao.LocalFolderDAO;
import dao.entity.LocalFileDO;
import dao.entity.LocalFolderDO;
import dao.factory.LocalFileDAOFactory;
import dao.factory.LocalFolderDAOFactory;
import manager.util.HibernateUtil;
import service.ReadFolderService;
import service.dto.DTOConvertor;
import service.dto.LocalFileDTO;
import service.dto.LocalFolderDTO;

public class ReadFolderServiceImpl implements ReadFolderService {
    
    private LocalFolderDAO localFolderDAO = LocalFolderDAOFactory.getInstance("hibernate");
    private LocalFileDAO localFileDAO = LocalFileDAOFactory.getInstance("hibernate");
    
    @Override
    public JSONObject serve(long userID, long folderID) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        JSONObject result = new JSONObject();
        
        /* 设置查询参数 */
        Map<String, Object> queryParam = new HashMap<>();
        if (folderID == 1L || folderID == 2L || folderID == 3L) {// 这三个文件夹ID为公用ID，分别代表网盘，保险箱，回收站
            queryParam.put("userID", userID);
        }
        queryParam.put("parent", folderID);
        
        List<LocalFolderDO> localFolderList = localFolderDAO.list(queryParam);
        List<LocalFolderDTO> folderDTOList = DTOConvertor.convertFolderList(localFolderList);
        result.put("folders", folderDTOList);
        
        List<LocalFileDO> localFileList = localFileDAO.list(queryParam);
        List<LocalFileDTO> fileDTOList = DTOConvertor.convertFileList(localFileList);
        result.put("files", fileDTOList);
        
        session.getTransaction().commit();
        return result;
    }
    
}
