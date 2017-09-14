package service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.FileDAO;
import dao.LocalFileDAO;
import dao.LocalFolderDAO;
import dao.entity.FileDO;
import dao.entity.LocalFileDO;
import dao.entity.LocalFolderDO;
import dao.factory.FileDAOFactory;
import dao.factory.LocalFileDAOFactory;
import dao.factory.LocalFolderDAOFactory;
import manager.exception.DBQueryException;
import service.GetFolderContentsService;
import web.dto.LocalFileDTO;
import web.dto.LocalFolderDTO;

public class GetFolderContentsServiceImpl implements GetFolderContentsService {

    @Override
    public List<LocalFolderDTO> getChildFolders(long userID, long folderID) {
        LocalFolderDAOFactory factory = new LocalFolderDAOFactory();
        LocalFolderDAO localFolderDAO = factory.getLocalFolderDAO("Hibernate");
        /* 设置查询参数 */
        Map<String, Object> params = new HashMap<>();
        if (folderID == 0L) {
            params.put("userID", userID);
        }
        params.put("parent", folderID);
        /* 根据查询参数获取查询结果 */
        List<LocalFolderDO> list = localFolderDAO.list(params);
        /* 将DO转换为DTO */
        List<LocalFolderDTO> result = new ArrayList<>();
        for (LocalFolderDO entity : list) {
            LocalFolderDTO dto = new LocalFolderDTO();
            dto.setId(entity.getId());
            dto.setLdtModified(entity.getLdtModified());
            dto.setLocalName(entity.getLocalName());
            result.add(dto);
        }
        return result;
    }

    @Override
    public List<LocalFileDTO> getChildFiles(long userID, long folderID) throws DBQueryException {
        LocalFileDAOFactory localFileDAOFactory = new LocalFileDAOFactory();
        LocalFileDAO localFileDAO = localFileDAOFactory.getLocalFileDAO("Hibernate");
        
        FileDAOFactory fileDAOFactory = new FileDAOFactory();
        FileDAO fileDAO = fileDAOFactory.getFileDAO("Hibernate");
        /* 设置LocalFile的查询参数 */
        Map<String, Object> localFileParams = new HashMap<>();
        if (folderID == 0L) {
            localFileParams.put("userID", userID);
        }
        localFileParams.put("parent", folderID);
        /* 根据LocalFile的查询参数获取查询结果 */
        List<LocalFileDO> localFileList = localFileDAO.list(localFileParams);
        /* 将DO转换为DTO */
        List<LocalFileDTO> result = new ArrayList<>();
        for (LocalFileDO entity : localFileList) {
            LocalFileDTO dto = new LocalFileDTO();
            dto.setId(entity.getId());
            dto.setLdtModified(entity.getLdtModified());
            dto.setFileID(entity.getFileID());
            dto.setLocalName(entity.getLocalName());
            dto.setLocalType(entity.getLocalType());
            /* 设置查询file的查询参数 */
            Map<String, Object> fileParams = new HashMap<>();
            fileParams.put("id", entity.getFileID());
            /* 根据FileID查询对应的真实文件，从而获取size */
            FileDO fileDO = fileDAO.get(fileParams);
            if (fileDO != null) {
                dto.setSize(fileDO.getSize());
            } else {
                throw new DBQueryException();
            }
            result.add(dto);
        }
        return result;
    }
    
}
