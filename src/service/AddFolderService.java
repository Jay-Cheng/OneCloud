package service;

import com.alibaba.fastjson.JSONObject;

import dao.entity.LocalFolderDO;

public interface AddFolderService {
    JSONObject serve(LocalFolderDO folder);
}
