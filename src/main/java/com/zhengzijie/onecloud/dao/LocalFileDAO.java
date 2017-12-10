package com.zhengzijie.onecloud.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhengzijie.onecloud.dao.entity.LocalFileDO;

public interface LocalFileDAO extends GenericDAO<LocalFileDO> {
    LocalFileDO getByPath(@Param("userID")Long userID, @Param("parent")Long parent
            , @Param("localName")String localName, @Param("localType")String localType);
    List<LocalFileDO> listByParent(Long parent);
    List<LocalFileDO> listRootContents(@Param("parent")Long parent, @Param("userID")Long userID);
    List<LocalFileDO> listRecentFile(Long userID);
    
    /** 使用listByLocalType()方法替代 */
    @Deprecated List<LocalFileDO> listDocument(long userID);
    @Deprecated List<LocalFileDO> listPhoto(long userID);
    @Deprecated List<LocalFileDO> listVideo(long userID);
    @Deprecated List<LocalFileDO> listAudio(long userID);
    
    /**
     * 列出ID=userID的用户的网盘内，所有本地类型在localTypes范围内的本地文件
     */
    List<LocalFileDO> listByLocalType(@Param("userID")Long userID, @Param("localTypes")String[] localTypes);
    
    List<LocalFileDO> listByName(@Param("userID")Long userID, @Param("name")String name);
}
