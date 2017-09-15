package manager.util;

import java.time.LocalDateTime;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.entity.LocalFolderDO;
import dao.entity.UserDO;

public class SaveTempData {
    
    private Session session = null;
    private Transaction t = null; 
    
    @Before
    public void before() {
        session = HibernateUtil.getSessionFactory().openSession();
        t = session.beginTransaction();
    }
    
    @After
    public void after() {
        t.commit();
        session.close();
    }
    
    @Test
    public void saveUser() {
        UserDO user = new UserDO();
        user.setLdtCreate(LocalDateTime.now());
        user.setLdtModified(LocalDateTime.now());
        user.setAccount("admin");
        user.setPassword("admin");
        user.setNickname("fakeaofu");
        user.setPhotoURL("users/photo/001.jpg");
        user.setUsedCapacity(1024*1024*1024L);
        
        session.persist(user);
    }
    
    
    @Test    
    public void saveLocalFolders() {
        
        LocalFolderDO disk = new LocalFolderDO();
        disk.setLdtCreate(LocalDateTime.now());
        disk.setLdtModified(LocalDateTime.now());
        disk.setLocalName("网盘");
        disk.setUserID(0L);
        disk.setParent(0L);
        
        LocalFolderDO safebox = new LocalFolderDO();
        safebox.setLdtCreate(LocalDateTime.now());
        safebox.setLdtModified(LocalDateTime.now());
        safebox.setLocalName("保险箱");
        safebox.setUserID(0L);
        safebox.setParent(0L);
        
        LocalFolderDO recycle = new LocalFolderDO();
        recycle.setLdtCreate(LocalDateTime.now());
        recycle.setLdtModified(LocalDateTime.now());
        recycle.setLocalName("回收站");
        recycle.setUserID(0L);
        recycle.setParent(0L);
        
        session.persist(disk);
        session.persist(safebox);
        session.persist(recycle);
    }
//    @Test    
//    public void saveFiles() {
//        
//        FileDO file1 = new FileDO();
//        file1.setLdtCreate(LocalDateTime.now());
//        file1.setLdtModified(LocalDateTime.now());
//        file1.setMd5("01234567890123456789012345678901");
//        file1.setSize(1024*1024*6);
//        file1.setType("unknown");
//        file1.setUrl("WEB-INF/upload/01234567890123456789012345678901");
//        
//        
//        session.persist(file1);
//    }
    
//    @Test   
//    public void saveLocalFiles() {
//        
//        LocalFileDO file1 = new LocalFileDO();
//        file1.setLdtCreate(LocalDateTime.now());
//        file1.setLdtModified(LocalDateTime.now());
//        file1.setUserID(1L);
//        file1.setFileID(1L);
//        file1.setLocalName("图片");
//        file1.setLocalType("jpg");
//        file1.setParent(0L);
//        
//        session.persist(file1);
//    }
//    @Test
//    public void update() {
//        LocalFolderDO folder1 = new LocalFolderDO();
//        folder1.setId(1L);
//        folder1.setLdtCreate(LocalDateTime.now());
//        folder1.setLdtModified(LocalDateTime.now());
//        folder1.setLocalName("新建文件夹11");
//        folder1.setUserID(1L);
//        folder1.setParent(0L);
//        
//        session.update(folder1);
//    }
}
