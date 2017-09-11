package manager.util;

import java.time.LocalDateTime;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.entity.FileDO;
import dao.entity.LocalFileDO;
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
    
//    @Test
    public void saveUser() {
        UserDO user = new UserDO();
        user.setGmtCreate(LocalDateTime.now());
        user.setGmtModified(LocalDateTime.now());
        user.setAccount("admin3");
        user.setPassword("admin");
        user.setNickname("fakeaofu");
        user.setPhotoURL("users/photo/003.jpg");
        user.setUsedCapacity(1024*1024*1024L);
        
        session.persist(user);
        System.out.println(user.getId());
    }
    
//    @Test    
    public void saveFiles() {
        
        FileDO file1 = new FileDO();
        file1.setGmtCreate(LocalDateTime.now());
        file1.setGmtModified(LocalDateTime.now());
        file1.setMd5("01234567890123456789012345678901");
        file1.setSize(1024*1024*6);
        file1.setType("xxx");
        file1.setUrl("WEB-INF/upload/01234567890123456789012345678901");
        
        FileDO file2 = new FileDO();
        file2.setGmtCreate(LocalDateTime.now());
        file2.setGmtModified(LocalDateTime.now());
        file2.setMd5("01234567890123456789012345678902");
        file2.setSize(1024*1024*7);
        file2.setType("xxx");
        file2.setUrl("WEB-INF/upload/01234567890123456789012345678902");
        
        FileDO file3 = new FileDO();
        file3.setGmtCreate(LocalDateTime.now());
        file3.setGmtModified(LocalDateTime.now());
        file3.setMd5("01234567890123456789012345678903");
        file3.setSize(1024*1024*3);
        file3.setType("xxx");
        file3.setUrl("WEB-INF/upload/01234567890123456789012345678903");
        
        session.persist(file1);
        session.persist(file2);
        session.persist(file3);
    }
    
//    @Test    
    public void saveLocalFolders() {
        
        LocalFolderDO folder1 = new LocalFolderDO();
        folder1.setGmtCreate(LocalDateTime.now());
        folder1.setGmtModified(LocalDateTime.now());
        folder1.setLocalName("新建文件夹1");
        folder1.setUserID(1L);
        folder1.setParent(0L);
        
        LocalFolderDO folder2 = new LocalFolderDO();
        folder2.setGmtCreate(LocalDateTime.now());
        folder2.setGmtModified(LocalDateTime.now());
        folder2.setLocalName("新建文件夹2");
        folder2.setUserID(1L);
        folder2.setParent(0L);
        
        LocalFolderDO folder3 = new LocalFolderDO();
        folder3.setGmtCreate(LocalDateTime.now());
        folder3.setGmtModified(LocalDateTime.now());
        folder3.setLocalName("新建文件夹3");
        folder3.setUserID(1L);
        folder3.setParent(0L);
        
        LocalFolderDO folder4 = new LocalFolderDO();
        folder4.setGmtCreate(LocalDateTime.now());
        folder4.setGmtModified(LocalDateTime.now());
        folder4.setLocalName("新建文件夹1");
        folder4.setUserID(1L);
        folder4.setParent(1L);
        
        session.persist(folder1);
        session.persist(folder2);
        session.persist(folder3);
        session.persist(folder4);
    }
    
//    @Test   
    public void saveLocalFiles() {
        
        LocalFileDO file1 = new LocalFileDO();
        file1.setGmtCreate(LocalDateTime.now());
        file1.setGmtModified(LocalDateTime.now());
        file1.setUserID(1L);
        file1.setFileID(1L);
        file1.setLocalName("图片");
        file1.setLocalType("jpg");
        file1.setParent(0L);
        
        LocalFileDO file2 = new LocalFileDO();
        file2.setGmtCreate(LocalDateTime.now());
        file2.setGmtModified(LocalDateTime.now());
        file2.setUserID(1L);
        file2.setFileID(2L);
        file2.setLocalName("视频");
        file2.setLocalType("mp4");
        file2.setParent(1L);
        
        LocalFileDO file3 = new LocalFileDO();
        file3.setGmtCreate(LocalDateTime.now());
        file3.setGmtModified(LocalDateTime.now());
        file3.setUserID(1L);
        file3.setFileID(3L);
        file3.setLocalName("文件");
        file3.setLocalType("");
        file3.setParent(4L);
        
        LocalFileDO file4 = new LocalFileDO();
        file4.setGmtCreate(LocalDateTime.now());
        file4.setGmtModified(LocalDateTime.now());
        file4.setUserID(1L);
        file4.setFileID(1L);
        file4.setLocalName("图片");
        file4.setLocalType("jpg");
        file4.setParent(2L);
        
        session.persist(file1);
        session.persist(file2);
        session.persist(file3);
        session.persist(file4);
    }
}
