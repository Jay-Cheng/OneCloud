package dao;

import java.time.LocalDateTime;

import dao.entity.LocalFileDO;

public interface LocalFileDAO extends GenericDAO<LocalFileDO>{
    LocalDateTime rename(LocalFileDO newDO);
}
