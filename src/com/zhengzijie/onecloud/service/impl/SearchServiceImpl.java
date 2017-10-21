package com.zhengzijie.onecloud.service.impl;

import java.util.List;

import org.hibernate.Session;

import com.alibaba.fastjson.JSONObject;
import com.zhengzijie.onecloud.dao.LocalFileDAO;
import com.zhengzijie.onecloud.dao.LocalFolderDAO;
import com.zhengzijie.onecloud.dao.entity.LocalFileDO;
import com.zhengzijie.onecloud.dao.entity.LocalFolderDO;
import com.zhengzijie.onecloud.dao.factory.LocalFileDAOFactory;
import com.zhengzijie.onecloud.dao.factory.LocalFolderDAOFactory;
import com.zhengzijie.onecloud.manager.util.HibernateUtil;
import com.zhengzijie.onecloud.service.SearchService;
import com.zhengzijie.onecloud.service.dto.DTOConvertor;
import com.zhengzijie.onecloud.service.dto.LocalFileDTO;
import com.zhengzijie.onecloud.service.dto.LocalFolderDTO;

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
