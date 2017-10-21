package com.zhengzijie.onecloud.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.zhengzijie.onecloud.dao.FileDAO;
import com.zhengzijie.onecloud.dao.LocalFileDAO;
import com.zhengzijie.onecloud.dao.LocalFolderDAO;
import com.zhengzijie.onecloud.dao.factory.FileDAOFactory;
import com.zhengzijie.onecloud.dao.factory.LocalFileDAOFactory;
import com.zhengzijie.onecloud.dao.factory.LocalFolderDAOFactory;

public abstract class DownloadService {
    
    protected FileDAO fileDAO = FileDAOFactory.getInstance("hibernate");
    protected LocalFileDAO localFileDAO = LocalFileDAOFactory.getInstance("hibernate");
    protected LocalFolderDAO localFolderDAO = LocalFolderDAOFactory.getInstance("hibernate");
    
    /** key是文件在客户端存储的路径，value是文件在服务器存储的路径 */
    protected Map<String, String> filePathMap = new HashMap<>();
    /** 空文件夹路径，用于压缩zip文件时生成空文件夹 */
    protected List<String> folderPathList = new ArrayList<>();
    /** 保存在客户端的文件名 */
    protected String filename = null;
    
    protected String filebase = null;
    /**
     * 根据客户端参数jsonArray生成客户端的文件结构
     */
    public abstract void init(JSONArray jsonArray, String filebase);
    
    /**
     * @return 保存在客户端的文件名
     */
    public abstract String getFilename();
    
    /**
     * 把所需文件压缩传给客户端
     * @param out response的OutputStream
     */
    public abstract void serve(OutputStream out) throws IOException;
    
}
