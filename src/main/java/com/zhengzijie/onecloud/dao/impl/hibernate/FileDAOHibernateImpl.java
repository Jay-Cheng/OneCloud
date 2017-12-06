package com.zhengzijie.onecloud.dao.impl.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.zhengzijie.onecloud.dao.FileDAO;
import com.zhengzijie.onecloud.dao.entity.FileDO;

@Repository
public class FileDAOHibernateImpl extends GenericDAOHibernateImpl<FileDO> implements FileDAO {

    public FileDAOHibernateImpl() {
        super(FileDO.class);
    }

    @Override
    public FileDO getByMd5(String md5) {
        Session session = sessionFactory.getCurrentSession();
        @SuppressWarnings("unchecked")
        Query<FileDO> query = session.createQuery("from FileDO file where file.md5 = :md5");
        query.setParameter("md5", md5);
        List<FileDO> list = query.list();
        if (list.isEmpty()) {
            return null;
        } else if (list.size() == 1) {
            return list.get(0);
        } else {
            throw new IllegalStateException("multiple result for md5=[" + md5 + "]");
        }
    }

}
