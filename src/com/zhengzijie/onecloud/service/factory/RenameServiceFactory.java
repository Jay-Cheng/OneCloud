package com.zhengzijie.onecloud.service.factory;

import com.zhengzijie.onecloud.service.RenameService;
import com.zhengzijie.onecloud.service.impl.RenameServiceFileImpl;
import com.zhengzijie.onecloud.service.impl.RenameServiceFolderImpl;

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
