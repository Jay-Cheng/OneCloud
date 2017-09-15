package dao;

import java.time.LocalDateTime;

import dao.entity.LocalFileDO;

public interface LocalFileDAO extends GenericDAO<LocalFileDO>, Moveable {
    LocalDateTime rename(LocalFileDO newDO);
}
