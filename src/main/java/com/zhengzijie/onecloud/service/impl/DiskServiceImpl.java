package com.zhengzijie.onecloud.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhengzijie.onecloud.dao.FileDAO;
import com.zhengzijie.onecloud.dao.LocalFileDAO;
import com.zhengzijie.onecloud.dao.LocalFolderDAO;
import com.zhengzijie.onecloud.dao.UserDAO;
import com.zhengzijie.onecloud.dao.entity.FileDO;
import com.zhengzijie.onecloud.dao.entity.LocalFileDO;
import com.zhengzijie.onecloud.dao.entity.LocalFolderDO;
import com.zhengzijie.onecloud.dao.entity.UserDO;
import com.zhengzijie.onecloud.manager.DTOConvertor;
import com.zhengzijie.onecloud.manager.Sorter;
import com.zhengzijie.onecloud.service.DiskService;
import com.zhengzijie.onecloud.service.dto.LocalFileDTO;
import com.zhengzijie.onecloud.service.dto.LocalFolderDTO;
import com.zhengzijie.onecloud.service.dto.UserDTO;

@Transactional @Service
public class DiskServiceImpl implements DiskService {
    
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private FileDAO fileDAO;
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

    @Override
    public Map<String, Object> move(List<Long> folders, List<Long> files, long dest) {
        List<LocalFolderDO> localFolderList = new ArrayList<>();
        for (int i = 0; i < folders.size(); i++) {
            LocalFolderDO localFolder = localFolderDAO.get(folders.get(i));
            localFolder.setParent(dest);
            localFolder.setLdtModified(LocalDateTime.now());
            localFolderDAO.update(localFolder);
            localFolderList.add(localFolder);
        }
        List<LocalFolderDTO> folderDTOList = convertor.convertFolderList(localFolderList);
        
        List<LocalFileDO> localFileList = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            LocalFileDO localFile = localFileDAO.get(files.get(i));
            localFile.setParent(dest);
            localFile.setLdtModified(LocalDateTime.now());
            localFileDAO.update(localFile);
            localFileList.add(localFile);
        }
        List<LocalFileDTO> fileDTOList = convertor.convertFileList(localFileList);
        
        Map<String, Object> result = new HashMap<>();
        result.put("folders", folderDTOList);
        result.put("files", fileDTOList);
        return result;
    }

    @Override
    public LocalFolderDTO renameFolder(long folderID, String localName) {
        LocalFolderDO folder = localFolderDAO.get(folderID);
        folder.setLocalName(localName);
        folder.setLdtModified(LocalDateTime.now());
        localFolderDAO.update(folder);
        
        return convertor.convertToDTO(folder);
    }

    @Override
    public LocalFileDTO renameFile(long fileID, String localName, String localType) {
        LocalFileDO file = localFileDAO.get(fileID);
        file.setLocalName(localName);
        file.setLocalType(localType);
        file.setLdtModified(LocalDateTime.now());
        localFileDAO.update(file);
        
        return  convertor.convertToDTO(file, null);
    }

    @Override
    public LocalFolderDTO newFolder(LocalFolderDO unsaved) {
        unsaved.setLdtCreate(LocalDateTime.now());
        unsaved.setLdtModified(unsaved.getLdtCreate());
        long id = localFolderDAO.save(unsaved);
        unsaved.setId(id);
        
        return convertor.convertToDTO(unsaved);
    }

    @Override
    public UserDTO shred(List<Long> folders, List<Long> files, long userID) {
        
        long removedCap = 0L;// 统计删除的总字节
        
        /* 删除文件 */
        for (int i = 0; i < files.size(); i++) {
            LocalFileDO localFile = localFileDAO.get(files.get(i));
            FileDO file = fileDAO.get(localFile.getFileID());
            removedCap += file.getSize();
            localFileDAO.remove(localFile);
        }
        
        /* 删除文件夹 */
        for (int i = 0; i < folders.size(); i++) {
            Queue<Long> queue = new LinkedList<>();
            queue.add(folders.get(i));
            while (!queue.isEmpty()) {
                Long parent = queue.poll();
                List<LocalFileDO> localFileList = localFileDAO.listByParent(parent);
                for (LocalFileDO localFile : localFileList) {
                    FileDO file = fileDAO.get(localFile.getFileID());
                    removedCap += file.getSize();
                    localFileDAO.remove(localFile);
                }

                List<LocalFolderDO> localFolderList = localFolderDAO.listByParent(parent);
                for (LocalFolderDO localFolder : localFolderList) {
                    queue.add(localFolder.getId());
                }
                localFolderDAO.remove(localFolderDAO.get(parent));
            }
        }
        
        /* 更新用户已用空间信息 */
        UserDO user = userDAO.get(userID);
        user.setUsedCapacity(user.getUsedCapacity() - removedCap);
        user.setLdtModified(LocalDateTime.now());
        userDAO.update(user);
        return convertor.convertToDTO(user);
    }
}
