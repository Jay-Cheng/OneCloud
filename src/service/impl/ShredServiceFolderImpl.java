package service.impl;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.hibernate.Session;

import com.alibaba.fastjson.JSONObject;

import dao.FileDAO;
import dao.LocalFileDAO;
import dao.LocalFolderDAO;
import dao.UserDAO;
import dao.entity.FileDO;
import dao.entity.LocalFileDO;
import dao.entity.LocalFolderDO;
import dao.entity.UserDO;
import dao.factory.FileDAOFactory;
import dao.factory.LocalFileDAOFactory;
import dao.factory.LocalFolderDAOFactory;
import dao.factory.UserDAOFactory;
import manager.util.HibernateUtil;
import service.ShredService;

public class ShredServiceFolderImpl implements ShredService {
    
    private UserDAO userDAO = UserDAOFactory.getInstance("hibernate");
    private FileDAO fileDAO = FileDAOFactory.getInstance("hibernate");
    private LocalFileDAO localFileDAO = LocalFileDAOFactory.getInstance("hibernate");
    private LocalFolderDAO localFolderDAO = LocalFolderDAOFactory.getInstance("hibernate");
    
    @Override
    public JSONObject serve(long id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        JSONObject result = new JSONObject();
        
        Queue<Long> queue = new LinkedList<>();
        queue.add(id);
        long deleteCap = 0L;// 删除的容量大小
        
        try {
            UserDO user = userDAO.read(localFolderDAO.read(id).getUserID());
            while (!queue.isEmpty()) {
                Long parent = queue.poll();
                List<LocalFileDO> localFileList = localFileDAO.listByParent(parent);
                for (LocalFileDO localFile :localFileList) {
                    FileDO file = fileDAO.read(localFile.getFileID());
                    deleteCap += file.getSize();
                    localFileDAO.delete(localFile);
                }
                
                List<LocalFolderDO> localFolderList = localFolderDAO.listByParent(parent);
                for (LocalFolderDO localFolder: localFolderList) {
                    queue.add(localFolder.getId());
                }
                
                localFolderDAO.delete(localFolderDAO.read(parent));
            }
            user.setUsedCapacity(user.getUsedCapacity() - deleteCap);
            user.setLdtModified(LocalDateTime.now());
            userDAO.update(user);
            
            
            result.put("cap", user.getUsedCapacity());
        } catch (Exception e) {
            session.getTransaction().rollback();
            result.put("status", 2);
            result.put("msg", "fail");
            return result;
        }
        
        result.put("status", 1);
        result.put("msg", "success");
        
        session.getTransaction().commit();
        return result;
    }
    
}
