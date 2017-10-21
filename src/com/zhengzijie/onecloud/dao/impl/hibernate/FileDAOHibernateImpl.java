package com.zhengzijie.onecloud.dao.impl.hibernate;

import com.zhengzijie.onecloud.dao.FileDAO;
import com.zhengzijie.onecloud.dao.entity.FileDO;

public class FileDAOHibernateImpl extends GenericDAOHibernateImpl<FileDO> implements FileDAO {

    public FileDAOHibernateImpl() {
        super(FileDO.class);
    }

}
