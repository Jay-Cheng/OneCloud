package com.zhengzijie.onecloud.service;

import com.zhengzijie.onecloud.manager.exception.IncorrectPasswordException;
import com.zhengzijie.onecloud.manager.exception.NoSuchUserException;
import com.zhengzijie.onecloud.service.dto.UserDTO;

public interface UserService {
    
    /**
     * @return JSON web token if login successfully.
     * @throws NoSuchUserException if username does not exist. 
     * @throws IncorrectPasswordException if password is incorrect.
     */
    String login(String username, String password) throws NoSuchUserException, IncorrectPasswordException;
    
    /**
     * 根据username获取用户信息
     * @return 包含用户基本信息的UserDTO对象
     */
    UserDTO getUser(String username);
}
