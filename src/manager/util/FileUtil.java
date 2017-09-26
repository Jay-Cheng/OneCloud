package manager.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.time.LocalDateTime;

import dao.entity.FileDO;
import dao.entity.LocalFileDO;

public class FileUtil {
    
    /**
     * 为文件添加后缀
     * @param file 原文件
     * @param mimeType 文件的mimeType
     * @return 文件添加后缀后的URL
     */
    public static String addSuffix(File file, String mimeType) {
        String suffix = getSuffix(mimeType);
        if (suffix.length() == 0) {
            return file.toString();
        }
        File dest = new File(file + "." + suffix);
        if (file.renameTo(dest)) {
            return dest.toString();
        } else {
            return file.toString();
        }
    }
    
    
    /**
     * 根据mimeType获取文件后缀
     */
    public static String getSuffix(String mimeType) {
        String suffix = "";
        switch (mimeType) {
        case "image/jpeg":                      suffix = "jpg";break;
        case "image/png":                       suffix = "png";break;
        case "image/gif":                       suffix = "gif";break;
        
        case "audio/mpeg":                      suffix = "md3";break;
        
        case "video/mp4":                       suffix = "mp4";break;
        
        case "text/plain":                      suffix = "txt";break;
        case "application/vnd.ms-powerpoint":   suffix = "ppt";break;
        case "application/msword":              suffix = "doc";break;
        case "application/vnd.ms-excel":        suffix = "xls";break;
        }
        return suffix;
    }
    
    /**
     * 获取文件真实的mimeType
     */
    public static String getRealType(File file) {
        String mimeType = null;
        try (InputStream is = new BufferedInputStream(new FileInputStream(file))) {
            mimeType = URLConnection.guessContentTypeFromStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (mimeType == null) {
            return "";
        } else {
            return mimeType;
        }
    }
    
    public static FileDO BuildFileDO(String fileBase, String md5) {
        
        FileDO fileDO = new FileDO();
        String url = fileBase + "\\" + md5;
        File file = new File(url);
        fileDO.setMd5(md5);
        fileDO.setSize((int)file.length());
        
        String mimeType = getRealType(file);
        url = addSuffix(file, mimeType);
        url = url.substring(url.indexOf("onecloud"));
                
        fileDO.setType(mimeType);
        fileDO.setUrl(url);
        fileDO.setLdtCreate(LocalDateTime.now());
        fileDO.setLdtModified(fileDO.getLdtCreate());
        
        return fileDO;
    }
    
    /**
     * 获取带后缀的完整文件名
     */
    public static String getFullFilename(LocalFileDO localFile) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(localFile.getLocalName());
        if (localFile.getLocalType().length() != 0) {
            stringBuilder.append(".");
            stringBuilder.append(localFile.getLocalType());
        }
        String filename = stringBuilder.toString();
        return filename;
    }

}
