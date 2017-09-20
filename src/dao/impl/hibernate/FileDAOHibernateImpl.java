package dao.impl.hibernate;

import dao.FileDAO;
import dao.entity.FileDO;

public class FileDAOHibernateImpl extends GenericDAOHibernateImpl<FileDO> implements FileDAO {

    public FileDAOHibernateImpl() {
        super(FileDO.class);
    }

}
