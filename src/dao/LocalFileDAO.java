package dao;

import java.util.List;

import dao.entity.LocalFileDO;

public interface LocalFileDAO extends GenericDAO<LocalFileDO> {
    LocalFileDO getByPath(long userID, long parent, String localName, String localType);
    List<LocalFileDO> listByParent(long parent);
}
