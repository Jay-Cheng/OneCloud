package com.zhengzijie.onecloud.dao.impl.hibernate;

import org.springframework.stereotype.Repository;

import com.zhengzijie.onecloud.dao.FileDAO;
import com.zhengzijie.onecloud.dao.entity.FileDO;

@Repository
public class FileDAOHibernateImpl extends GenericDAOHibernateImpl<FileDO> implements FileDAO {

    public FileDAOHibernateImpl() {
        super(FileDO.class);
    }

}
