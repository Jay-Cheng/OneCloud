package manager.util;

import java.time.LocalDateTime;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
    }
    
    @Test
    public void SaveUser() {
        UserDO user = new UserDO();
        user.setGmtCreate(LocalDateTime.now());
        user.setGmtModified(LocalDateTime.now());
        user.setAccount("admin");
        user.setPassword("admin");
        user.setNickname("fakeaofu");
        user.setPhotoURL("users/photo/001.jpg");
        user.setUsedCapacity(1024*1024*1024L);
        
        session.persist(user);
        session.close();
    }
    
    
    
    
    
    
    
    
    
    
    
    
}
