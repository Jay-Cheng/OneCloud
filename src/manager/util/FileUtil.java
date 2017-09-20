package manager.util;

import java.io.File;
import java.time.LocalDateTime;

import dao.entity.FileDO;

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
}
