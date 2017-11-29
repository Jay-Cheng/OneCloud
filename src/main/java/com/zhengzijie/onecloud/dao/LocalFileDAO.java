package com.zhengzijie.onecloud.dao;

import java.util.List;

import com.zhengzijie.onecloud.dao.entity.LocalFileDO;

public interface LocalFileDAO extends GenericDAO<LocalFileDO> {
    LocalFileDO getByPath(long userID, long parent, String localName, String localType);
    List<LocalFileDO> listByParent(long parent);
    
    List<LocalFileDO> listRecentFile(long userID);
    List<LocalFileDO> listDocument(long userID);
    List<LocalFileDO> listPhoto(long userID);
    List<LocalFileDO> listVideo(long userID);
    List<LocalFileDO> listAudio(long userID);
    
    List<LocalFileDO> listByName(long userID, String name);
}
