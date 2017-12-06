package com.zhengzijie.onecloud.manager.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zhengzijie.onecloud.dao.UserDAO;
import com.zhengzijie.onecloud.dao.entity.UserDO;
import com.zhengzijie.onecloud.manager.constant.StorageConst;

@Component
public class ParamChecker {
    @Autowired
    private UserDAO userDAO;
    
    /**
     * @return {@code true} if user has enough storage, {@code false} otherwise
     */
    @Transactional(readOnly=true)
    public boolean isUserStorageEnough(long userID, long size) {
        UserDO user = userDAO.get(userID);
        if (user.getUsedCapacity() + size > StorageConst.COMMON_USER_STORAGE_SIZE) {
            return false;
        }
        return true;
    }
}
