package service.impl;

import java.time.LocalDateTime;

import dao.LocalFolderDAO;
import dao.entity.LocalFolderDO;
import dao.factory.LocalFolderDAOFactory;
import service.RenameService;

public class RenameFolderServiceImpl implements RenameService<LocalFolderDO> {

    @Override
    public LocalDateTime rename(LocalFolderDO newFolder) {
        LocalFolderDAO dao = new LocalFolderDAOFactory().getLocalFolderDAO("Hibernate");
        LocalDateTime now;
        try {            
            now = dao.rename(newFolder);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        return now;
    }
    
    
}
