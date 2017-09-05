package service;

import java.util.List;

import manager.exception.DBQueryException;
import web.dto.LocalFileDTO;
import web.dto.LocalFolderDTO;

public interface GetFolderContentsService {
    List<LocalFolderDTO> getChildFolders(long userID, long folderID);
    List<LocalFileDTO> getChildFiles(long userID, long folderID) throws DBQueryException;
}
