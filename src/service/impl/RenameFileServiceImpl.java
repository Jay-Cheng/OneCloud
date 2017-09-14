package service.impl;

import java.time.LocalDateTime;

import dao.LocalFileDAO;
import dao.entity.LocalFileDO;
import dao.factory.LocalFileDAOFactory;
import service.RenameService;

public class RenameFileServiceImpl implements RenameService<LocalFileDO> {

    @Override
    public LocalDateTime rename(LocalFileDO newFile) {
        LocalFileDAO dao = new LocalFileDAOFactory().getLocalFileDAO("Hibernate");
        LocalDateTime now;
        try {            
            now = dao.rename(newFile);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        return now;
    }

}
