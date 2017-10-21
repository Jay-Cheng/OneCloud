package com.zhengzijie.onecloud.service.dto;

import java.util.ArrayList;
import java.util.List;

import com.zhengzijie.onecloud.dao.FileDAO;
import com.zhengzijie.onecloud.dao.entity.FileDO;
import com.zhengzijie.onecloud.dao.entity.LocalFileDO;
import com.zhengzijie.onecloud.dao.entity.LocalFolderDO;
import com.zhengzijie.onecloud.dao.entity.UserDO;
import com.zhengzijie.onecloud.dao.factory.FileDAOFactory;

public class DTOConvertor {
    
    private static FileDAO fileDAO = FileDAOFactory.getInstance("hibernate");
    
    public static UserDTO convert(UserDO entity) {
        if (entity != null) {
            UserDTO dto = new UserDTO();
            dto.setId(entity.getId());
            dto.setAccount(entity.getAccount());
            dto.setNickname(entity.getNickname());
            dto.setPhotoURL(entity.getPhotoURL());
            dto.setUsedCapacity(entity.getUsedCapacity());
            return dto;
        }
        return null;
    }
    
    public static LocalFolderDTO convert(LocalFolderDO entity) {
        if (entity != null) {
            LocalFolderDTO dto = new LocalFolderDTO();
            dto.setId(entity.getId());
            dto.setLdtModified(entity.getLdtModified());
            dto.setLocalName(entity.getLocalName());
            return dto;
        } else {
            return null;
        }
    }
    
    public static List<LocalFolderDTO> convertFolderList(List<LocalFolderDO> entityList) {
        if (entityList != null) {
            List<LocalFolderDTO> dtoList = new ArrayList<>();
            for (LocalFolderDO entity : entityList) {
                LocalFolderDTO dto = convert(entity);
                dtoList.add(dto);
            }
            return dtoList;
        } else {
            return null;
        }
    }
    
    public static LocalFileDTO convert(LocalFileDO localFileEntity, FileDO fileEntity) {
        if (localFileEntity != null) {
            LocalFileDTO dto = new LocalFileDTO();
            dto.setId(localFileEntity.getId());
            dto.setLdtModified(localFileEntity.getLdtModified());
            dto.setFileID(localFileEntity.getFileID());
            dto.setLocalName(localFileEntity.getLocalName());
            dto.setLocalType(localFileEntity.getLocalType());
            if (fileEntity == null) {
                fileEntity = fileDAO.read(dto.getFileID());
            }
            dto.setSize(fileEntity.getSize());
            dto.setUrl(fileEntity.getUrl());
            return dto;
        } else {
            return null;
        }
    }
    
    public static List<LocalFileDTO> convertFileList(List<LocalFileDO> entityList) {
        if (entityList != null) {
            List<LocalFileDTO> dtoList = new ArrayList<>();
            for (LocalFileDO entity : entityList) {
                LocalFileDTO dto = convert(entity, null);
                dtoList.add(dto);
            }
            return dtoList;
        } else {
            return null;
        }
    }
}
