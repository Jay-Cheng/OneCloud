package service.impl;

import java.util.HashMap;
import java.util.Map;

import dao.FileDAO;
import dao.entity.FileDO;
import dao.factory.FileDAOFactory;
import manager.exception.DBQueryException;
import service.MD5CheckService;

public class MD5CheckServiceImpl implements MD5CheckService {
    
    /**
     * 检查MD5为对应值的文件在数据库是否存在
     * @return 若存在，返回该文件对象，否则返回null
     * @throws DBQueryException If 查询到了多个结果
     */
    @Override
    public FileDO check(String md5) throws DBQueryException {
        FileDAOFactory fileFactory = new FileDAOFactory();
        FileDAO fileDAO = fileFactory.getFileDAO("Hibernate");
        Map<String, Object> params = new HashMap<>();
        params.put("md5", md5);
        FileDO file = fileDAO.get(params);
        return file;
    }
    
}
