package service.dto;

import java.util.ArrayList;
import java.util.List;

import dao.FileDAO;
import dao.entity.LocalFileDO;
import dao.entity.LocalFolderDO;
import dao.entity.UserDO;
import dao.factory.FileDAOFactory;

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
    
    public static LocalFileDTO convert(LocalFileDO entity, Integer size) {
        if (entity != null) {
            LocalFileDTO dto = new LocalFileDTO();
            dto.setId(entity.getId());
            dto.setLdtModified(entity.getLdtModified());
            dto.setFileID(entity.getFileID());
            dto.setLocalName(entity.getLocalName());
            dto.setLocalType(entity.getLocalType());
            if (size == null) {
                dto.setSize(fileDAO.read(dto.getFileID()).getSize());
            } else {
                dto.setSize(size);
            }
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
