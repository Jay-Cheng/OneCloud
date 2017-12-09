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
import com.zhengzijie.onecloud.dao.LocalFolderDAO;
import com.zhengzijie.onecloud.dao.UserDAO;
import com.zhengzijie.onecloud.dao.entity.FileDO;
import com.zhengzijie.onecloud.dao.entity.LocalFileDO;
import com.zhengzijie.onecloud.dao.entity.LocalFolderDO;
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
        FileDO getTest = fileDAO.get(29L);
        assertTrue(306407 == getTest.getSize());
        
        FileDO saveTest = new FileDO();
        saveTest.setLdtCreate(LocalDateTime.now());
        saveTest.setLdtModified(LocalDateTime.now());
        saveTest.setMd5("save" + System.currentTimeMillis());
        saveTest.setSize(System.currentTimeMillis());
        saveTest.setType("save test");
        saveTest.setUrl("save" + System.currentTimeMillis());
        fileDAO.save(saveTest);
        System.out.println(saveTest.getId());// 数据库生成的自增主键
        
        FileDO updateTest = fileDAO.get(33L);
        updateTest.setMd5("update" + System.currentTimeMillis());
        updateTest.setUrl("update" + System.currentTimeMillis());
        fileDAO.update(updateTest);
        
        FileDO removeTest = fileDAO.get(saveTest.getId());
        fileDAO.remove(removeTest);
        
        System.out.println(fileDAO.getByMd5("8486f0c3cd2d48fd0b24eb1045e338f6"));
    }
    
    @Autowired
    private LocalFolderDAO localFolderDAO;
    
    @Transactional @Test
    public void testLocalFolderDAO() {
        LocalFolderDO getTest = localFolderDAO.get(1L);
        assertEquals("home", getTest.getLocalName());
        
        LocalFolderDO saveTest = new LocalFolderDO();
        saveTest.setLdtCreate(LocalDateTime.now());
        saveTest.setLdtModified(LocalDateTime.now());
        saveTest.setLocalName("save test");
        saveTest.setUserID(System.currentTimeMillis());
        saveTest.setParent(System.currentTimeMillis());
        localFolderDAO.save(saveTest);
        System.out.println(saveTest.getId());// 数据库生成的自增主键
        
        LocalFolderDO updateTest = localFolderDAO.get(11L);
        updateTest.setUserID(System.currentTimeMillis());
        updateTest.setParent(System.currentTimeMillis());
        updateTest.setLocalName("update test");
        localFolderDAO.update(updateTest);
        
        LocalFolderDO removeTest = localFolderDAO.get(saveTest.getId());
        localFolderDAO.remove(removeTest);
        
        System.out.println(localFolderDAO.listByParent(1L));
        System.out.println(localFolderDAO.listByName(1L, "图"));
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
