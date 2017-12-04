package com.zhengzijie.onecloud.service;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.Part;

import com.zhengzijie.onecloud.dao.entity.FileDO;
import com.zhengzijie.onecloud.dao.entity.LocalFileDO;

public interface FileService {
    
    /**
     * 检查md5值对应的文件是否存在，若存在则插入、更新相应的数据，不再继续上传；
     * 否则继续上传
     */
    Map<String, Object> serveFirstPart(Part part, String md5, LocalFileDO localFile) throws IOException;
    
    /**
     * 保存最后一个文件块，并插入、更新相应的数据
     */
    Map<String, Object> serveLastPart(Part part, LocalFileDO localFile, FileDO file) throws IOException;
    
    /**
     * 保存文件块到文件系统
     */
    void savePart(Part part, String md5) throws IOException;
    
    /**
     * 取消上传，删除上传到一半的文件
     */
    void cancel(String md5);
    
    /**
     * 获取上传到一半的文件的大小，返回给客户端以实现断点续传
     */
    Long resmue(String md5);
}
