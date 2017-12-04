package com.zhengzijie.onecloud.dao;

import java.util.List;

import com.zhengzijie.onecloud.dao.entity.LocalFileDO;

public interface LocalFileDAO extends GenericDAO<LocalFileDO> {
    LocalFileDO getByPath(long userID, long parent, String localName, String localType);
    List<LocalFileDO> listByParent(long parent);
    
    List<LocalFileDO> listRecentFile(long userID);
    
    /** 使用listByLocalType()方法替代 */
    @Deprecated List<LocalFileDO> listDocument(long userID);
    @Deprecated List<LocalFileDO> listPhoto(long userID);
    @Deprecated List<LocalFileDO> listVideo(long userID);
    @Deprecated List<LocalFileDO> listAudio(long userID);
    
    /**
     * 列出ID=userID的用户的网盘内，所有本地类型在localTypes范围内的本地文件
     */
    List<LocalFileDO> listByLocalType(long userID, String[] localTypes);
    
    List<LocalFileDO> listByName(long userID, String name);
}
