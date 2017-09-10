package service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import dao.FileDAO;
import dao.LocalFileDAO;
import dao.entity.FileDO;
import dao.entity.LocalFileDO;
import dao.factory.FileDAOFactory;
import dao.factory.LocalFileDAOFactory;
import manager.exception.DBQueryException;
import service.MD5CheckService;

public class MD5CheckServiceImpl implements MD5CheckService {
    /**
     * 检查MD5为对应值的文件记录在数据库中是否存在
     * 若存在，且插入本地文件记录成功，返回true
     * 否则返回false
     */
    @Override
    public boolean check(String md5, LocalFileDO localfile) throws DBQueryException {
        FileDAOFactory fileFactory = new FileDAOFactory();
        FileDAO fileDAO = fileFactory.getFileDAO("Hibernate");
        Map<String, Object> params = new HashMap<>();
        params.put("md5", md5);
        FileDO file = fileDAO.get(params);
        if (file == null) {
            return false;
        } else {
            /* 插入本地文件记录 */
            LocalFileDAOFactory localfileDAOFactory = new LocalFileDAOFactory();
            LocalFileDAO localfileDAO = localfileDAOFactory.getLocalFileDAO("Hibernate");
            localfile.setFileID(file.getId());
            localfile.setGmtCreate(LocalDateTime.now());
            localfile.setGmtModified(LocalDateTime.now());
            if (localfileDAO.save(localfile)) {
                return true;
            } else {
                // TODO 处理插入数据失败的情况
                return true;
            }
        }
    }
    
    
}
