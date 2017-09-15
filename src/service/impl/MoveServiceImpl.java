package service.impl;

import java.time.LocalDateTime;

import dao.Moveable;
import dao.factory.LocalFileDAOFactory;
import dao.factory.LocalFolderDAOFactory;
import service.MoveService;

public class MoveServiceImpl implements MoveService {

    @Override
    public LocalDateTime serve(Long id, Long to, String type) {
        Moveable moveable;
        if ("folder".equals(type)) {
            moveable = new LocalFolderDAOFactory().getLocalFolderDAO("Hibernate");
        } else {
            moveable = new LocalFileDAOFactory().getLocalFileDAO("Hibernate");
        }
        LocalDateTime ldtModified = moveable.move(id, to);
        return ldtModified;
    }

}
