package com.zhengzijie.onecloud.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhengzijie.onecloud.dao.UserDAO;
import com.zhengzijie.onecloud.dao.entity.UserDO;
import com.zhengzijie.onecloud.manager.DataConvertor;
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
    
    @Override 
    public UserDTO login(String username, String password) throws NoSuchUserException, IncorrectPasswordException {
        
        /* 设置查询参数，根据参数查询数据 */
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("username", username);
        UserDO user = userDAO.get(queryParam);
        
        if (user == null) {
            throw new NoSuchUserException();
        } else if (user.getPassword().equals(password)) {
            return dataConvertor.convertToDTO(user);
        } else {
            throw new IncorrectPasswordException();
        }
        
    }
}
