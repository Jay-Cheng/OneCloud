package com.zhengzijie.onecloud.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.hibernate.Session;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhengzijie.onecloud.dao.entity.FileDO;
import com.zhengzijie.onecloud.dao.entity.LocalFileDO;
import com.zhengzijie.onecloud.dao.entity.LocalFolderDO;
import com.zhengzijie.onecloud.manager.util.FileUtil;
import com.zhengzijie.onecloud.manager.util.HibernateUtil;
import com.zhengzijie.onecloud.service.DownloadService;

public class DownloadServiceImpl extends DownloadService {

    @Override
    public void init(JSONArray jsonArray, String filebase) {
        this.filebase = filebase;
        String parentPath = "";// 初始路径为空
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject element = jsonArray.getJSONObject(i);
            int type = element.getInteger("t");
            long id = element.getLongValue("id");
            if (type == 0) {// type = folder
                LocalFolderDO folder = localFolderDAO.read(id);
                if (filename == null) {
                    filename = folder.getLocalName();
                }
                generateFolderPath(parentPath, folder);
            } else if (type == 1) {// type = file
                LocalFileDO file = localFileDAO.read(id);
                if (filename == null) {
                    filename = FileUtil.getFullFilename(file);
                }
                generateFilePath(parentPath, file);
            } else {
                throw new IllegalArgumentException("illegal argument 'type': must be 0 or 1");
            }
        }
        session.getTransaction().commit();
        filename += "等" + jsonArray.size() + "个文件.zip";
    }

    @Override
    public String getFilename() {
        return filename;
    }
    
    @Override
    public void serve(OutputStream out) throws IOException {
        ZipOutputStream zos = null;
        FileInputStream fis = null;
        try {
            zos = new ZipOutputStream(out);
            for (Entry<String, String> entry : filePathMap.entrySet()) {
                zos.putNextEntry(new ZipEntry(entry.getKey()));
                File file = new File(filebase + File.separator + entry.getValue());
                try {
                    fis = new FileInputStream(file);
                    int hasRead;
                    byte[] buffer = new byte[1024];
                    while ((hasRead = fis.read(buffer)) != -1) {
                        zos.write(buffer, 0, hasRead);
                    }
                } finally {
                    if (fis != null) {
                        fis.close();
                    }
                }
            }
            /* 往zip里添加空文件夹 */
            for (String emptyFolder : folderPathList) {
                zos.putNextEntry(new ZipEntry(emptyFolder));
            }
        } finally {
            zos.close();
        }

    }
    
    /**
     * 生成当前文件夹路径，若文件夹为空，将其路径加入folderPathList
     * @param parentPath 上一级路径
     * @param folder 当前文件夹实体对象
     */
    private void generateFolderPath(String parentPath, LocalFolderDO folder) {
        if (parentPath.length() > 0) {
            parentPath = parentPath + File.separator + folder.getLocalName();
        } else {
            parentPath = folder.getLocalName();
        }
        
        boolean emptyFolder = true;
        
        List<LocalFolderDO> subfolders = localFolderDAO.listByParent(folder.getId());
        for (LocalFolderDO subfolder : subfolders) {
            emptyFolder = false;
            generateFolderPath(parentPath, subfolder);
        }
        
        List<LocalFileDO> subfiles = localFileDAO.listByParent(folder.getId());
        for (LocalFileDO subfile : subfiles) {
            emptyFolder = false;
            generateFilePath(parentPath, subfile);
        }
        
        if (emptyFolder) {
            parentPath += File.separator;// 需要在路径后面加一个分隔符才会生成空文件夹
            folderPathList.add(parentPath);
        }
    }
    
    /**
     * 生成当前文件的本地路径并获取文件的服务器路径
     * 把两个路径分别作为key,value存入filePathMap中
     * @param parentPath 上一级路径
     * @param localFile 当前文件实体对象
     */
    private void generateFilePath(String parentPath, LocalFileDO localFile) {
        String filename = FileUtil.getFullFilename(localFile);
        String filePath;
        if (parentPath.length() > 0) {
            filePath = parentPath + File.separator + filename;
        } else {
            filePath = filename;
        }
        FileDO file = fileDAO.read(localFile.getFileID());
        filePathMap.put(filePath, file.getUrl());
    }
}
