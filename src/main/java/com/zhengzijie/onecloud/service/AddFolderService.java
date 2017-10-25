package com.zhengzijie.onecloud.service;

import com.alibaba.fastjson.JSONObject;
import com.zhengzijie.onecloud.dao.entity.LocalFolderDO;

public interface AddFolderService {
    JSONObject serve(LocalFolderDO folder);
}
