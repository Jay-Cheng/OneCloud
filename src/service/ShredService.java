package service;

import java.util.HashMap;
import java.util.Map;


import dao.UserDAO;
import dao.entity.UserDO;
import dao.factory.UserDAOFactory;
import manager.exception.DBQueryException;

public interface ShredService {
    boolean shred(long id);
    default long getCapAfterShred(long uid) throws DBQueryException {
        Map<String, Object> params = new HashMap<>();
        params.put("id", uid);
        UserDAO dao = new UserDAOFactory().getUserDAO("Hibernate");
        UserDO user = dao.get(params);
        return user.getUsedCapacity();
    }
}
