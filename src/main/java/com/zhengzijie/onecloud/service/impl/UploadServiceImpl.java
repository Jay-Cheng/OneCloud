package com.zhengzijie.onecloud.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.zhengzijie.onecloud.dao.FileDAO;
import com.zhengzijie.onecloud.dao.LocalFileDAO;
import com.zhengzijie.onecloud.dao.UserDAO;
import com.zhengzijie.onecloud.dao.entity.FileDO;
import com.zhengzijie.onecloud.dao.entity.LocalFileDO;
import com.zhengzijie.onecloud.dao.entity.UserDO;
import com.zhengzijie.onecloud.manager.DTOConvertor;
import com.zhengzijie.onecloud.service.UploadService;

@Service
public class UploadServiceImpl implements UploadService {
    
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private FileDAO fileDAO;
    @Autowired
    private LocalFileDAO localFileDAO;
    @Autowired
    private DTOConvertor convertor;
    
    @Override @Transactional(isolation=Isolation.SERIALIZABLE)
    public synchronized Map<String, Object> serveFirstPart(Part part, String md5, LocalFileDO localFile) throws IOException {
        FileDO file = fileDAO.getByMd5(md5);
        if (file != null) {
            /* 服务器存在该文件，执行秒传逻辑 */
            Map<String, Object> result = new HashMap<>();

            localFile.setFileID(file.getId());
            /* 检查客户端上传路径下是否存在同名文件 */
            LocalFileDO duplicate = localFileDAO.getByPath(localFile.getUserID()
                    , localFile.getParent(),localFile.getLocalName(), localFile.getLocalType());
            while (duplicate != null) {
                /*
                 * 如果存在同名文件，且两文件对应的真实文件相同，则返回相应的业务状态码和消息，在客户端执行对应的操作
                 * 如果存在同名文件，但两文件对应的真实文件不同，则为上传文件的文件名添加数字下标，然后继续检查新文件名是否重名，直到不存在重名文件为止
                 */
                if (duplicate.getFileID() == localFile.getFileID()) {
                    result.put("status_code", 111);System.out.println("111");
                    result.put("msg", "localfile already exists");
                    return result;
                } else {
                    localFile.setLocalName(resolveLocalNameConflict(localFile.getLocalName()));
                    duplicate = localFileDAO.getByPath(localFile.getUserID()
                            , localFile.getParent(),localFile.getLocalName(), localFile.getLocalType());
                }
            }
            /* 新建一行LocalFile数据，并更新用户使用空间 */
            localFile.setLdtCreate(LocalDateTime.now());
            localFile.setLdtModified(localFile.getLdtCreate());
            localFile.setId(localFileDAO.save(localFile));
            
            result.put("status_code", 222);System.out.println("222");
            result.put("msg", "instant uploading");
            result.put("file", convertor.convertToDTO(localFile, file));
            result.put("user", convertor.convertToDTO(updateUserCap(localFile.getUserID(), file.getSize(), true)));
            return result;
        }
        /* 服务器不存在该文件，开始文件上传 */
        savePart(part, md5);
        return null;
    }

    @Override @Transactional(isolation=Isolation.SERIALIZABLE)
    public synchronized Map<String, Object> serveLastPart(Part part, LocalFileDO localFile, FileDO file) throws IOException {
        savePart(part, file.getMd5());
        /* 保存新上传文件的信息 */
        file.setUrl(URL_ROOT + file.getMd5());
        file.setLdtCreate(LocalDateTime.now());
        file.setLdtModified(file.getLdtCreate());
        long fileID = fileDAO.save(file);
        
        localFile.setFileID(fileID);
        /* 如果存在重名，则为文件名添加数字下标，直到无重名为止 */
        while (localFileDAO.getByPath(localFile.getUserID()
                , localFile.getParent(), localFile.getLocalName(), localFile.getLocalType()) != null) {
            localFile.setLocalName(resolveLocalNameConflict(localFile.getLocalName()));
        }
        /* 新建一行LocalFile数据，并更新用户使用空间 */
        localFile.setLdtCreate(LocalDateTime.now());
        localFile.setLdtModified(localFile.getLdtCreate());
        localFile.setId(localFileDAO.save(localFile));

        Map<String, Object> result = new HashMap<>();
        result.put("status_code", 333);System.out.println("333");
        result.put("msg", "uploading accomplished");
        result.put("file", convertor.convertToDTO(localFile, file));
        result.put("user", convertor.convertToDTO(updateUserCap(localFile.getUserID(), file.getSize(), true)));
        return result;
    }

    @Override
    public void savePart(Part part, String md5) throws IOException {
        File file = new File(FILE_BASE + md5);
        FileOutputStream fos = new FileOutputStream(file, true);
        BufferedInputStream bis = new BufferedInputStream(part.getInputStream());
        byte[] buf = new byte[1024 * 1024];
        int hasRead;
        while ((hasRead = bis.read(buf)) != -1) {
            fos.write(buf, 0, hasRead);
        }
        bis.close();
        fos.close();
    }
    
    @Override @Transactional(readOnly = true)
    public synchronized void cancel(String md5) {
        if (fileDAO.getByMd5(md5) == null) {
            File file = new File(FILE_BASE + md5);
            if (file.exists()) {
                file.delete();
            }
        }
    }
    
    @Override
    public Long resmue(String md5) {
        File file = new File(FILE_BASE + md5);
        if (file.exists()) {
            Long uploadedBytes = file.length();System.out.println(uploadedBytes);
            return uploadedBytes;
        }
        return null;
    }
    
    /**
     * 当文件上传路径发生重名时，为文件名添加一个数字下标防止冲突
     * @param localName 原文件名
     * @return 原文件名加上一个数字下标的字符串
     */
    private String resolveLocalNameConflict(String localName) {
        if (localName.length() > 2) {
            String end = localName.substring(localName.length() - 3);
            if (end.matches("\\(\\d\\)") && end.charAt(1) != '0') {// 判断localName是否以形如“(n)”的字符串结尾
                int i = Integer.parseInt(end.substring(1, 2));// 括号中间的数字
                i++;
                StringBuilder sb = new StringBuilder();
                sb.append(localName.substring(0, localName.length() - 3));
                sb.append('(').append(i).append(')');
                
                return sb.toString();
            }
        }
        return localName + "(1)";
    }
    
    /**
     * 更新ID=userID的用户所使用的空间，更新量为size
     * @param plus = true 增加使用空间，plus=false减少使用空间
     * @return 更新后的用户对象
     */
    private UserDO updateUserCap(long userID, long size, boolean plus) {
        UserDO user = userDAO.get(userID);
        if (plus) {
            user.setUsedCapacity(user.getUsedCapacity() + size);
        } else {
            user.setUsedCapacity(user.getUsedCapacity() - size);
        }
        user.setLdtModified(LocalDateTime.now());
        userDAO.update(user);
        return user;
    }
}
