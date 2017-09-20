package dao.impl.hibernate;

import dao.UserDAO;
import dao.entity.UserDO;

public class UserDAOHibernateImpl extends GenericDAOHibernateImpl<UserDO> implements UserDAO {

    public UserDAOHibernateImpl() {
        super(UserDO.class);
    }
    
}
