package manager.util;

import java.io.File;

import dao.entity.FileDO;
import dao.entity.LocalFileDO;
import web.dto.LocalFileDTO;

public class FileUtil {
    public static String getRealType(File file) {
        String type = "unknown";
        return type;
    }
    
    public static LocalFileDTO BulidLocalFileDTO(FileDO file, LocalFileDO localfile) {
        LocalFileDTO localfileDTO = new LocalFileDTO();
        
        localfileDTO.setId(localfile.getId());
        localfileDTO.setGmtModified(localfile.getGmtModified());
        localfileDTO.setFileID(localfile.getFileID());
        localfileDTO.setLocalName(localfile.getLocalName());
        localfileDTO.setLocalType(localfile.getLocalType());
        localfileDTO.setSize(file.getSize());
        
        return localfileDTO;
    }
}
