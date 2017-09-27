package service.impl;

import java.util.List;

import org.hibernate.Session;

import com.alibaba.fastjson.JSONObject;

import dao.LocalFileDAO;
import dao.LocalFolderDAO;
import dao.entity.LocalFileDO;
import dao.entity.LocalFolderDO;
import dao.factory.LocalFileDAOFactory;
import dao.factory.LocalFolderDAOFactory;
import manager.util.HibernateUtil;
import service.SearchService;
import service.dto.DTOConvertor;
import service.dto.LocalFileDTO;
import service.dto.LocalFolderDTO;

public class SearchServiceImpl implements SearchService {
    
    private LocalFolderDAO localFolderDAO = LocalFolderDAOFactory.getInstance("hibernate");
    private LocalFileDAO localFileDAO = LocalFileDAOFactory.getInstance("hibernate");
    
    @Override
    public JSONObject serve(long userID, String name) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        JSONObject result = new JSONObject();
        
        List<LocalFolderDO> localFolderList = localFolderDAO.listByName(userID, name);
        List<LocalFolderDTO> folderDTOList = DTOConvertor.convertFolderList(localFolderList);
        result.put("folders", folderDTOList);
        
        List<LocalFileDO> localFileList = localFileDAO.listByName(userID, name);
        List<LocalFileDTO> fileDTOList = DTOConvertor.convertFileList(localFileList);
        result.put("files", fileDTOList);
        
        session.getTransaction().commit();
        return result;
    }

}
