package com.zhengzijie.onecloud.service;

import java.util.Map;

public interface DiskService {
    Map<String, Object> getMenuContents(long userID, String menu);
    Map<String, Object> getFolderContents(long userID, long folderID, int sortType);
    Map<String, Object> search(long userID, String input);
}
