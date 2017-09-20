package dao.impl.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import dao.LocalFolderDAO;
import dao.entity.LocalFolderDO;
import manager.util.HibernateUtil;

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

}
