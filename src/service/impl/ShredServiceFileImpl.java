package service.impl;

import dao.LocalFileDAO;
import dao.factory.LocalFileDAOFactory;
import service.ShredService;

public class ShredServiceFileImpl implements ShredService {

    @Override
    public boolean shred(long id) {
        LocalFileDAO dao = new LocalFileDAOFactory().getLocalFileDAO("Hibernate");
        return dao.remove(id);
    }
    
}
