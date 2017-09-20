package service.factory;

import service.RenameService;
import service.impl.RenameServiceFileImpl;
import service.impl.RenameServiceFolderImpl;

public class RenameServiceFactory {
    
    public static RenameService getService(String type) {
        if ("folder".equalsIgnoreCase(type)) {
            return new RenameServiceFolderImpl();
        } else if ("file".equalsIgnoreCase(type)) {
            return new RenameServiceFileImpl();
        } else {
            return null;
        }
    }
} 
