package manager.util;

import java.io.File;
import java.time.LocalDateTime;

import dao.entity.FileDO;
import dao.entity.LocalFileDO;

public class FileUtil {
    
    public static String getRealType(File file) {
        String type = "unknown";
        return type;
    }
    
    public static FileDO BuildFileDO(String fileBase, String md5) {
        
        FileDO fileDO = new FileDO();
        String url = fileBase + "\\" + md5;
        File file = new File(url);
        fileDO.setMd5(md5);
        fileDO.setSize((int)file.length());
        fileDO.setUrl(url);
        fileDO.setType(getRealType(file));
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
