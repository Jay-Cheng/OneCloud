package com.zhengzijie.onecloud.service.factory;

import com.zhengzijie.onecloud.service.MoveService;
import com.zhengzijie.onecloud.service.impl.MoveServiceFileImpl;
import com.zhengzijie.onecloud.service.impl.MoveServiceFolderImpl;

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
