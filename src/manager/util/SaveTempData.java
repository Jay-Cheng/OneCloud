package manager.util;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.LocalFileDAO;
import dao.LocalFolderDAO;
import dao.entity.LocalFileDO;
import dao.entity.LocalFolderDO;
import dao.entity.UserDO;
import dao.factory.LocalFileDAOFactory;
import dao.factory.LocalFolderDAOFactory;
import dao.impl.hibernate.LocalFileDAOHibernateImpl;

public class SaveTempData {
    
    private Session session = null;
    private Transaction t = null; 
    
//    @Before
    public void before() {
        session = HibernateUtil.getSessionFactory().openSession();
        t = session.beginTransaction();
    }
    
//    @After
    public void after() {
        t.commit();
        session.close();
    }
    
//    @Test
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
    
    
//    @Test    
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
}
