package service.factory;

import service.MoveService;
import service.impl.MoveServiceFileImpl;
import service.impl.MoveServiceFolderImpl;

public class MoveServiceFactory {
    public static MoveService getService(String type) {
        if ("folder".equalsIgnoreCase(type)) {
            return new MoveServiceFolderImpl();
        } else if ("file".equalsIgnoreCase(type)) {
            return new MoveServiceFileImpl();
        } else {
            return null;
        }
    }
}
