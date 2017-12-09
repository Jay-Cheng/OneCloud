package com.zhengzijie.onecloud.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhengzijie.onecloud.dao.entity.LocalFolderDO;

public interface LocalFolderDAO extends GenericDAO<LocalFolderDO> {
    List<LocalFolderDO> listByParent(Long parent);
    List<LocalFolderDO> listByName(@Param("userID")Long userID, @Param("name")String name);
}
