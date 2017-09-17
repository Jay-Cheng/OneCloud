package service.factory;

import service.ShredService;
import service.impl.ShredServiceFileImpl;
import service.impl.ShredServiceFolderImpl;

public class ShredServiceFactory {
    public ShredService getService(String type) {
        if ("folder".equals(type)) {
            return new ShredServiceFolderImpl();
        } else if ("file".equals(type)) {
            return new ShredServiceFileImpl();
        } else {
            return null;
        }
    }
}
