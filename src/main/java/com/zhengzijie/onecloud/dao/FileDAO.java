package com.zhengzijie.onecloud.dao;

import com.zhengzijie.onecloud.dao.entity.FileDO;

public interface FileDAO extends GenericDAO<FileDO> {
    FileDO getByMd5(String md5);
}
