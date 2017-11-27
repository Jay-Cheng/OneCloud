package com.zhengzijie.onecloud.service;

import com.zhengzijie.onecloud.manager.exception.IncorrectPasswordException;
import com.zhengzijie.onecloud.manager.exception.NoSuchUserException;
import com.zhengzijie.onecloud.web.dto.UserDTO;

public interface UserService {
    
    /**
     * @return userDTO object if login successfully.
     * @throws NoSuchUserException if username does not exist. 
     * @throws IncorrectPasswordException  if password is incorrect.
     */
    UserDTO login(String username, String password) throws NoSuchUserException, IncorrectPasswordException;
}
