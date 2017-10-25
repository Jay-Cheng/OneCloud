package com.zhengzijie.onecloud.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.alibaba.fastjson.JSONObject;
import com.zhengzijie.onecloud.dao.LocalFileDAO;
import com.zhengzijie.onecloud.dao.LocalFolderDAO;
import com.zhengzijie.onecloud.dao.entity.LocalFileDO;
import com.zhengzijie.onecloud.dao.entity.LocalFolderDO;
import com.zhengzijie.onecloud.dao.factory.LocalFileDAOFactory;
import com.zhengzijie.onecloud.dao.factory.LocalFolderDAOFactory;
import com.zhengzijie.onecloud.manager.util.HibernateUtil;
import com.zhengzijie.onecloud.service.ReadFolderService;
import com.zhengzijie.onecloud.service.SortService;
import com.zhengzijie.onecloud.service.dto.DTOConvertor;
import com.zhengzijie.onecloud.service.dto.LocalFileDTO;
import com.zhengzijie.onecloud.service.dto.LocalFolderDTO;
import com.zhengzijie.onecloud.service.factory.SortServiceFactory;

public class ReadFolderServiceImpl implements ReadFolderService {
    
    private LocalFolderDAO localFolderDAO = LocalFolderDAOFactory.getInstance("hibernate");
    private LocalFileDAO localFileDAO = LocalFileDAOFactory.getInstance("hibernate");
    
    @Override
    public JSONObject serve(long userID, long folderID, int sortType) {
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
        LocalFolderDTO[] folderDTOArray = folderDTOList.toArray(new LocalFolderDTO[folderDTOList.size()]);
        
        List<LocalFileDO> localFileList = localFileDAO.list(queryParam);
        List<LocalFileDTO> fileDTOList = DTOConvertor.convertFileList(localFileList);
        LocalFileDTO[] fileDTOArray = fileDTOList.toArray(new LocalFileDTO[fileDTOList.size()]);
        
        SortService sorter = SortServiceFactory.getService(sortType);
        sorter.serve(folderDTOArray);
        sorter.serve(fileDTOArray);
        
        result.put("folders", folderDTOArray);
        result.put("files", fileDTOArray);
        
        session.getTransaction().commit();
        return result;
    }
    
}
