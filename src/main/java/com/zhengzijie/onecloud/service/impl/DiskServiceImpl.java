package com.zhengzijie.onecloud.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhengzijie.onecloud.dao.LocalFileDAO;
import com.zhengzijie.onecloud.dao.LocalFolderDAO;
import com.zhengzijie.onecloud.dao.entity.LocalFileDO;
import com.zhengzijie.onecloud.dao.entity.LocalFolderDO;
import com.zhengzijie.onecloud.manager.DTOConvertor;
import com.zhengzijie.onecloud.manager.Sorter;
import com.zhengzijie.onecloud.service.DiskService;
import com.zhengzijie.onecloud.service.dto.LocalFileDTO;
import com.zhengzijie.onecloud.service.dto.LocalFolderDTO;

@Transactional @Service
public class DiskServiceImpl implements DiskService {
    
    @Autowired
    private LocalFileDAO localFileDAO;
    @Autowired
    private LocalFolderDAO localFolderDAO;
    @Autowired
    private Sorter sorter;
    @Autowired
    private DTOConvertor convertor;
    
    @Override @Transactional(readOnly = true)
    public Map<String, Object> getMenuContents(long userID, String menu) {
        List<LocalFileDO> files = null;
        int sortType = 0;// 默认按字母排序
        switch(menu) {
        case "recent":files = localFileDAO.listRecentFile(userID);sortType = 1;break;// 按时间排序
        case "doc":files = localFileDAO.listDocument(userID);break;
        case "photo":files = localFileDAO.listPhoto(userID);break;
        case "video":files = localFileDAO.listVideo(userID);break;
        case "audio":files = localFileDAO.listAudio(userID);break;
        
        case "disk":return getFolderContents(userID, 1, 0);
//        case "safebox": return getFolderContents(userID, 2, 0);// TODO 功能未实现
        case "recycle":return getFolderContents(userID, 3, 0);
            
        case "share":break;// TODO 功能未实现
        }
        
        if (files == null) {
            throw new IllegalArgumentException("Illegal argument: " + menu);
        }
        
        List<LocalFileDTO> fileDTOList = convertor.convertFileList(files);
        LocalFileDTO[] fileDTOArray = fileDTOList.toArray(new LocalFileDTO[fileDTOList.size()]);
        sorter.sort(fileDTOArray, sortType);
        
        Map<String, Object> result = new HashMap<>();
        result.put("files", fileDTOArray);
        return result;
    }
    
    @Override @Transactional(readOnly = true)
    public Map<String, Object> getFolderContents(long userID, long folderID, int sortType) {
        /* 设置查询参数 */
        Map<String, Object> queryParam = new HashMap<>();
        if (folderID == 1L || folderID == 2L || folderID == 3L) {// 这三个文件夹ID为公用ID，分别代表网盘，保险箱，回收站
            queryParam.put("userID", userID);
        }
        queryParam.put("parent", folderID);
        
        List<LocalFolderDO> localFolderList = localFolderDAO.list(queryParam);
        List<LocalFolderDTO> folderDTOList = convertor.convertFolderList(localFolderList);
        LocalFolderDTO[] folderDTOArray = folderDTOList.toArray(new LocalFolderDTO[folderDTOList.size()]);
        sorter.sort(folderDTOArray, sortType);
        
        List<LocalFileDO> localFileList = localFileDAO.list(queryParam);
        List<LocalFileDTO> fileDTOList = convertor.convertFileList(localFileList);
        LocalFileDTO[] fileDTOArray = fileDTOList.toArray(new LocalFileDTO[fileDTOList.size()]);
        sorter.sort(fileDTOArray, sortType);
        
        Map<String, Object> result = new HashMap<>();
        result.put("folders", folderDTOArray);
        result.put("files", fileDTOArray);
        return result;
    }

    @Override @Transactional(readOnly = true)
    public Map<String, Object> search(long userID, String input) {
        List<LocalFolderDO> localFolderList = localFolderDAO.listByName(userID, input);
        List<LocalFolderDTO> folderDTOList = convertor.convertFolderList(localFolderList);
        
        List<LocalFileDO> localFileList = localFileDAO.listByName(userID, input);
        List<LocalFileDTO> fileDTOList = convertor.convertFileList(localFileList);
        
        Map<String, Object> result = new HashMap<>();
        result.put("folders", folderDTOList);
        result.put("files", fileDTOList);
        return result;
    }
}
