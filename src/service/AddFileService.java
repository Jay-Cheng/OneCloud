package service;

import dao.entity.LocalFileDO;
import manager.exception.DBQueryException;
import web.dto.LocalFileDTO;

public interface AddFileService {
    int add(boolean uploaded, String md5, LocalFileDO localfile, long userID, int size) throws DBQueryException;
    LocalFileDTO getLocalFileDTO();
}
