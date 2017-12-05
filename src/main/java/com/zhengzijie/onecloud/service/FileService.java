package com.zhengzijie.onecloud.service;

import java.io.File;

import com.zhengzijie.onecloud.dao.entity.LocalFileDO;

public interface FileService {
    /** 服务端保存所有文件的根路径 */
    String FILE_BASE = "Programming/Java/apache-tomcat-8.5.23/webapps/OneCloud_Upload" + File.separator;
    /** 所有上传文件URL的根 */
    String URL_ROOT = "http://localhost:8080/OneCloud_Upload/";
    
    default String getFullFilename(LocalFileDO localFile) {
        return localFile.getLocalName() + "." + localFile.getLocalType();
    }
}
