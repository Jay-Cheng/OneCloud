package com.zhengzijie.onecloud.service.factory;

import com.zhengzijie.onecloud.service.ShredService;
import com.zhengzijie.onecloud.service.impl.ShredServiceFileImpl;
import com.zhengzijie.onecloud.service.impl.ShredServiceFolderImpl;

public class ShredServiceFactory {
    public static ShredService getService(String type) {
        if ("folder".equals(type)) {
            return new ShredServiceFolderImpl();
        } else if ("file".equals(type)) {
            return new ShredServiceFileImpl();
        } else {
            return null;
        }
    }
}
