package service.impl;

import java.io.File;
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
import manager.util.FileUtil;
import service.AddFileService;
import web.dto.LocalFileDTO;

public class AddFileServiceImpl implements AddFileService {
    
    private FileDAO fileDAO;
    private LocalFileDAO localfileDAO;
    
    private LocalFileDTO localfileDTO = null; 
    
    public AddFileServiceImpl() {
        fileDAO = new FileDAOFactory().getFileDAO("Hibernate");
        localfileDAO = new LocalFileDAOFactory().getLocalFileDAO("Hibernate");
    }
    
    /**
     * 根据不同的参数决定是否在数据库中插入新的localfile和file数据
     * @param uploaded 用户是否进行过实际的文件传输（访问了UploadServlet）
     * @param md5 用户需要上传文件的md5值
     * @param localfile 用户持有该文件的标记
     */
    @Override
    public int add(boolean uploaded, String md5, LocalFileDO localfile) throws DBQueryException {
        /*
         * 如果用户进行过实际的文件传输（说明服务器不存在用户上传的文件）
         * 插入新的FileDO
         * 并添加该用户对该文件的持有标记（插入对应的LocalFileDO）
         */
        if (uploaded) {
            FileDO file = BuildFileDOByMD5(md5);
            fileDAO.save(file);
            localfile.setFileID(file.getId());
            localfile.setLdtCreate(LocalDateTime.now());
            localfile.setLdtModified(LocalDateTime.now());
            localfileDAO.save(localfile);
            localfileDTO = FileUtil.BulidLocalFileDTO(file, localfile);
            return 1;// 文件刚刚上传
        } else {
            /* 判断服务器是否存在该文件 */
            FileDO file = new MD5CheckServiceImpl().check(md5);
            if (file == null) {
                return 2;// 文件不存在，需要上传
            } else {
                /*
                 * 假设用户在同一个目录下上传了一个重名文件
                 * 由于数据表设计不允许这种情况的产生
                 * 因此要判断localfile是否已经存在，防止插入重复键
                 */
                Map<String, Object> params = new HashMap<>();
                params.put("userID", localfile.getUserID());
                params.put("parent", localfile.getParent());
                params.put("localName", localfile.getLocalName());
                params.put("localType", localfile.getLocalType());
                LocalFileDO duplicate = localfileDAO.get(params);
                if (duplicate != null) {
                    return 5;// 文件和标记都存在
                } else {
                    localfile.setFileID(file.getId());
                    localfile.setLdtCreate(LocalDateTime.now());
                    localfile.setLdtModified(LocalDateTime.now());
                    localfileDAO.save(localfile);
                    localfileDTO = FileUtil.BulidLocalFileDTO(file, localfile);
                    return 3;// 文件已存在，添加标记
                }
            }
        }
    }
    
    @Override
    public LocalFileDTO getLocalFileDTO() {
        return localfileDTO;
    }
    
    /**
     * 由md5生成一个FileDO数据库实体类
     * @return FileDO数据库实体类
     */
    private FileDO BuildFileDOByMD5(String md5) {
        FileDO fileDO = new FileDO();
        String url = "D:\\Java\\apache-tomcat-8.5.14\\webapps\\OneCloud\\WEB-INF\\upload\\" + md5;
        File file = new File(url);
        
        fileDO.setMd5(md5);
        fileDO.setSize((int)file.length());
        fileDO.setUrl(url);
        fileDO.setType(FileUtil.getRealType(file));
        fileDO.setLdtCreate(LocalDateTime.now());
        fileDO.setLdtModified(LocalDateTime.now());
        
        return fileDO;
    }

}
