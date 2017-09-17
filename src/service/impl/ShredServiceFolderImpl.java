package service.impl;

import dao.LocalFolderDAO;
import dao.factory.LocalFolderDAOFactory;
import service.ShredService;

public class ShredServiceFolderImpl implements ShredService {

    @Override
    public boolean shred(long id) {
        
        LocalFolderDAO dao = new LocalFolderDAOFactory().getLocalFolderDAO("Hibernate");
        return dao.remove(id);
    }
    
}
