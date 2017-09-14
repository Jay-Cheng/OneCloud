package service;

import dao.entity.FileDO;
import manager.exception.DBQueryException;

public interface MD5CheckService {
    FileDO check(String md5) throws DBQueryException;
}
