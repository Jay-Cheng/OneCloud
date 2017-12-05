package com.zhengzijie.onecloud.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface DownloadService extends FileService {
    
    /**
     * 生成zip文件名
     */
    String generateZipFilename(List<Long> files, List<Long> folders);
    
    /**
     * 将所有文件及文件夹打包成一个zip文件返回给客户端
     */
    void download(List<Long> files, List<Long> folders, OutputStream out) throws IOException;
}
