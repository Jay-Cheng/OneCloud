package dao;

import java.time.LocalDateTime;

import dao.entity.LocalFolderDO;

public interface LocalFolderDAO extends GenericDAO<LocalFolderDO> {
    LocalDateTime rename(LocalFolderDO newDO);
}
