package service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.hibernate.Session;

import com.alibaba.fastjson.JSONObject;

import dao.FileDAO;
import dao.LocalFileDAO;
import dao.UserDAO;
import dao.entity.FileDO;
import dao.entity.LocalFileDO;
import dao.entity.UserDO;
import dao.factory.FileDAOFactory;
import dao.factory.LocalFileDAOFactory;
import dao.factory.UserDAOFactory;
import manager.util.FileUtil;
import manager.util.HibernateUtil;
import service.AddFileService;
import service.dto.DTOConvertor;
import service.dto.LocalFileDTO;

public class AddFileServiceImpl implements AddFileService {
    
    private static ReentrantLock lock = new ReentrantLock();
    
    private UserDAO userDAO = UserDAOFactory.getInstance("hibernate");
    private FileDAO fileDAO = FileDAOFactory.getInstance("hibernate");
    private LocalFileDAO localFileDAO = LocalFileDAOFactory.getInstance("hibernate");
    
    public final String fileBase;
    
    public AddFileServiceImpl(String fileBase) {
        this.fileBase = fileBase;
    }
    
    /**
     * 根据不同的参数决定是否在数据库中插入新的localfile和file数据
     * @param userID 用户ID
     * @param uploaded 用户是否进行过实际的文件传输（访问了UploadServlet）
     * @param md5 用户需要上传文件的md5值
     * @param localfile 用户持有该文件的标记
     */
    @Override
    public JSONObject serve(long userID, boolean uploaded, String md5, LocalFileDO localFile) {
        lock.lock();
        
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        JSONObject result = new JSONObject();
        
        /*
         * 如果用户进行过实际的文件传输（说明服务器不存在用户上传的文件）
         * 插入新的FileDO
         * 并添加该用户对该文件的持有标记（插入对应的LocalFileDO）
         */
        try {
            if (uploaded) {
                
                FileDO file = FileUtil.BuildFileDO(fileBase, md5);
                long fileID = fileDAO.create(file);
                localFile.setFileID(fileID);
                localFile.setLdtCreate(LocalDateTime.now());
                localFile.setLdtModified(localFile.getLdtCreate());
                localFileDAO.create(localFile);
                LocalFileDTO fileDTO = DTOConvertor.convert(localFile, file.getSize());
                /* 更新用户的存储空间 */
                increaseUsedCap(userID, file.getSize());
                
                result.put("status", 1);
                result.put("msg", "File is uploaded by current user");
                result.put("data", fileDTO);
            } else {
                /* 判断服务器是否存在该文件 */
                Map<String, Object> queryParam = new HashMap<>();
                queryParam.put("md5", md5);
                FileDO file = fileDAO.get(queryParam);
                if (file == null) {
                    result.put("status", 2);
                    result.put("msg", "File does not exist, need to upload");
                } else {
                    /*
                     * 假设用户在同一个目录下上传了一个重名文件
                     * 由于数据表设计不允许这种情况的产生
                     * 因此要判断localfile是否已经存在，防止插入重复键
                     */
                    LocalFileDO duplicate = localFileDAO.getByPath(localFile.getUserID(), localFile.getParent(), localFile.getLocalName(), localFile.getLocalType());
                    if (duplicate != null) {
                        result.put("status", 5);
                        result.put("msg", "LocalFile of current path already exists");
                    } else {
                        localFile.setFileID(file.getId());
                        localFile.setLdtCreate(LocalDateTime.now());
                        localFile.setLdtModified(localFile.getLdtCreate());
                        localFileDAO.create(localFile);
                        LocalFileDTO fileDTO = DTOConvertor.convert(localFile, file.getSize());
                        /* 更新用户的存储空间 */
                        increaseUsedCap(userID, file.getSize());
                        result.put("status", 3);
                        result.put("msg", "File exists, add LocalFile");
                        result.put("data", fileDTO);
                    }
                }
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (lock.isLocked()) {
                lock.unlock();
            }
        }
        return result;
    }
    
    /**
     * 增加用户的已使用的存储空间
     * @param userID 用户ID
     * @param size 增加的文件大小
     */
    private void increaseUsedCap(long userID, int size) {
        UserDO user = userDAO.read(userID);
        user.setUsedCapacity(user.getUsedCapacity() + size);
        user.setLdtModified(LocalDateTime.now());
        userDAO.update(user);
    }
}
