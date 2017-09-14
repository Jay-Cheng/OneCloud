package service.impl;

import java.time.LocalDateTime;

import dao.LocalFolderDAO;
import dao.entity.LocalFolderDO;
import dao.factory.LocalFolderDAOFactory;
import service.AddFolderService;

public class AddFolderServiceImpl implements AddFolderService {

    @Override
    public void serve(LocalFolderDO folder) {
        LocalFolderDAO dao = new LocalFolderDAOFactory().getLocalFolderDAO("Hibernate");
        
        LocalDateTime now = LocalDateTime.now();
        folder.setLdtCreate(now);
        folder.setLdtModified(now);
        
        try {
            dao.save(folder);
        } catch (Exception e) {
            folder = null;
            e.printStackTrace();
        }
    }

}
