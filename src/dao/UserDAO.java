package dao;

import dao.entity.UserDO;

public interface UserDAO extends GenericDAO<UserDO> {
    void updateCap(long id, int size);
}
