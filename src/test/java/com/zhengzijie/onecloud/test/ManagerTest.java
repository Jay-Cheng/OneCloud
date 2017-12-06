package com.zhengzijie.onecloud.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.zhengzijie.onecloud.config.RootConfig;
import com.zhengzijie.onecloud.config.WebConfig;
import com.zhengzijie.onecloud.dao.LocalFileDAO;
import com.zhengzijie.onecloud.dao.LocalFolderDAO;
import com.zhengzijie.onecloud.dao.UserDAO;
import com.zhengzijie.onecloud.dao.entity.LocalFolderDO;
import com.zhengzijie.onecloud.manager.DTOConvertor;
import com.zhengzijie.onecloud.service.dto.LocalFileDTO;
import com.zhengzijie.onecloud.service.dto.LocalFolderDTO;
import com.zhengzijie.onecloud.service.dto.UserDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { WebConfig.class,RootConfig.class } )
@SuppressWarnings("unused")
public class ManagerTest {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private LocalFileDAO localFileDAO;
    @Autowired
    private LocalFolderDAO localFolderDAO;
    @Autowired
    private DTOConvertor convertor;
    
    @Transactional @Test
    public void testDataConvertor() {
        UserDTO userDTO = convertor.convertToDTO(userDAO.get(1L));
        assertEquals("admin", userDTO.getUsername());
        
        LocalFolderDTO localFolderDTO = convertor.convertToDTO(localFolderDAO.get(1L));
        System.out.println(localFolderDTO);
        
        LocalFileDTO localFileDTO = convertor.convertToDTO(localFileDAO.get(2L), null);
        System.out.println(localFileDTO);
    }
}








