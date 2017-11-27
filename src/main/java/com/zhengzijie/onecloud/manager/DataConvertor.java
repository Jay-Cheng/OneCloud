package com.zhengzijie.onecloud.manager;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhengzijie.onecloud.dao.entity.UserDO;
import com.zhengzijie.onecloud.web.dto.UserDTO;

@Component
public class DataConvertor {
    
    @Autowired
    private ModelMapper modelMapper;
    
    public UserDTO convertToDTO(UserDO user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
