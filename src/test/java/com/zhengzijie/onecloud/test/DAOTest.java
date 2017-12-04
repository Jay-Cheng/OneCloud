package com.zhengzijie.onecloud.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

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
import com.zhengzijie.onecloud.dao.LocalFileDAO;
import com.zhengzijie.onecloud.dao.UserDAO;
import com.zhengzijie.onecloud.dao.entity.LocalFileDO;
import com.zhengzijie.onecloud.manager.DTOConvertor;
import com.zhengzijie.onecloud.service.dto.UserDTO;

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
        assertEquals("admin", userDAO.getByUsername("admin").getUsername());
    }
    
    @Autowired
    private FileDAO fileDAO;
    
    @Transactional @Test
    public void testFileDAO() {
        assertTrue(143001L == fileDAO.get(2L).getSize());
    }
    
    @Autowired
    private LocalFileDAO localFileDAO;
    
    @Transactional @Test
    public void testLocalFileDAO() {
        
        String[] localTypes = {"txt", "mp4", "mp3"};
        List<LocalFileDO> localFileList = localFileDAO.listByLocalType(1L, localTypes);
        
        for (LocalFileDO file : localFileList) {
            System.out.println(file);
        }
    }
    
}
