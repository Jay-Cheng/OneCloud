package service;

import dao.entity.LocalFileDO;
import manager.exception.DBQueryException;

public interface MD5CheckService {
    boolean check(String md5, LocalFileDO localfile) throws DBQueryException;
}
