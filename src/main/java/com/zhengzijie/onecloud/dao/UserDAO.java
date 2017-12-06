package com.zhengzijie.onecloud.dao;

import com.zhengzijie.onecloud.dao.entity.UserDO;

public interface UserDAO extends GenericDAO<UserDO> {
    UserDO getByUsername(String username);
}
