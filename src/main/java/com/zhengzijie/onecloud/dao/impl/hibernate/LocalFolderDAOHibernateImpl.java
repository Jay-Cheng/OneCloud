package com.zhengzijie.onecloud.dao.impl.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.zhengzijie.onecloud.dao.LocalFolderDAO;
import com.zhengzijie.onecloud.dao.entity.LocalFolderDO;
import com.zhengzijie.onecloud.manager.util.HibernateUtil;

public class LocalFolderDAOHibernateImpl extends GenericDAOHibernateImpl<LocalFolderDO> implements LocalFolderDAO {

    public LocalFolderDAOHibernateImpl() {
        super(LocalFolderDO.class);
    }

    @Override
    public List<LocalFolderDO> listByParent(long parent) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        @SuppressWarnings("unchecked")
        Query<LocalFolderDO> query = session.createQuery("from LocalFolderDO folder where folder.parent = :parent");
        query.setParameter("parent", parent);
        List<LocalFolderDO> result = query.list();
        return result;
    }

    @Override
    public List<LocalFolderDO> listByName(long userID, String name) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        @SuppressWarnings("unchecked")
        Query<LocalFolderDO> query = session.createQuery("from LocalFolderDO folder where folder.parent!=2 and folder.parent!=3 and folder.userID=:userID and folder.localName like :name");
        query.setParameter("userID", userID);
        query.setParameter("name", "%" + name + "%");
        List<LocalFolderDO> result = query.list();
        return result;
    }

}
