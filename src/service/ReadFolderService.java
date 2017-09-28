package service;

import com.alibaba.fastjson.JSONObject;

public interface ReadFolderService {
    JSONObject serve(long userID, long folderID, int sortType);
}
