package dao;

import java.util.List;

import dao.entity.LocalFileDO;

public interface LocalFileDAO extends GenericDAO<LocalFileDO> {
    LocalFileDO getByPath(long userID, long parent, String localName, String localType);
    List<LocalFileDO> listByParent(long parent);
    
    List<LocalFileDO> listRecentFile(long userID);
    List<LocalFileDO> listDocument(long userID);
    List<LocalFileDO> listPicture(long userID);
    List<LocalFileDO> listVideo(long userID);
    List<LocalFileDO> listMusic(long userID);
    
    List<LocalFileDO> listByName(long userID, String name);
}
