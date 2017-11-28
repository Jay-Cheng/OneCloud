package com.zhengzijie.onecloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhengzijie.onecloud.dao.UserDAO;
import com.zhengzijie.onecloud.dao.entity.UserDO;
import com.zhengzijie.onecloud.manager.DataConvertor;
import com.zhengzijie.onecloud.manager.JWTUtil;
import com.zhengzijie.onecloud.manager.exception.IncorrectPasswordException;
import com.zhengzijie.onecloud.manager.exception.NoSuchUserException;
import com.zhengzijie.onecloud.service.UserService;
import com.zhengzijie.onecloud.web.dto.UserDTO;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    private DataConvertor dataConvertor;
    
    @Override @Transactional(readOnly = true)
    public String login(String username, String password) throws NoSuchUserException, IncorrectPasswordException {
        UserDO user = userDAO.getByUsername(username);
        
        if (user == null) {
            throw new NoSuchUserException();
        } else if (user.getPassword().equals(password)) {
            return JWTUtil.generateToken(username);
        } else {
            throw new IncorrectPasswordException();
        }
    }

    @Override @Transactional(readOnly = true)
    public UserDTO getUser(String username) {
        UserDO user = userDAO.getByUsername(username);
        if (user == null) {
            return null;
        } else {
            return dataConvertor.convertToDTO(user);
        }
    }
}
