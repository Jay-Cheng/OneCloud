package com.zhengzijie.onecloud.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
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
import com.zhengzijie.onecloud.dao.entity.UserDO;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { WebConfig.class,RootConfig.class } )
public class DAOTest {
    
    @Autowired
    private UserDAO userDAO;
    
    @Transactional @Test
    public void testUserDAO() {
        UserDO getTest = userDAO.get(1L);
        assertEquals("admin", getTest.getUsername());
        
        UserDO saveTest = new UserDO();
        saveTest.setLdtCreate(LocalDateTime.now());
        saveTest.setLdtModified(LocalDateTime.now());
        saveTest.setUsername("save" + System.currentTimeMillis());
        saveTest.setPassword("password");
        saveTest.setNickname("nickname");
        saveTest.setPhotoURL("save" + System.currentTimeMillis());
        saveTest.setUsedCapacity(10000L);
        userDAO.save(saveTest);
        System.out.println(saveTest.getId());// 数据库生成的自增主键
        
        UserDO updateTest = userDAO.get(2L);
        updateTest.setUsername("update" + System.currentTimeMillis());
        updateTest.setPhotoURL("update" + System.currentTimeMillis());
        userDAO.update(updateTest);
        
        UserDO removeTest = userDAO.get(9L);
        userDAO.remove(removeTest);
        
        System.out.println(userDAO.getByUsername("admin"));
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
