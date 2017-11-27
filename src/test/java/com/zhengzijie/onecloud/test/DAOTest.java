package com.zhengzijie.onecloud.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.zhengzijie.onecloud.config.RootConfig;
import com.zhengzijie.onecloud.config.WebConfig;
import com.zhengzijie.onecloud.dao.FileDAO;
import com.zhengzijie.onecloud.dao.UserDAO;
import com.zhengzijie.onecloud.manager.DataConvertor;
import com.zhengzijie.onecloud.web.dto.UserDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { WebConfig.class,RootConfig.class } )
@SuppressWarnings("unused")
public class DAOTest {
    
    @Autowired
    private UserDAO userDAO;
    
    @Transactional @Test
    public void testUserDAO() {
        assertEquals("admin", userDAO.get(1L).getUsername());
    }
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Transactional @Test
    public void testModelMapper() {
        UserDTO userDTO = modelMapper.map(userDAO.get(1L), UserDTO.class);
        assertEquals("admin", userDTO.getUsername());
    }
    
    @Autowired
    private FileDAO fileDAO;
    
    @Transactional @Test
    public void testFileDAO() {
        assertTrue(143001L == fileDAO.get(2L).getSize());
    }
    
}
