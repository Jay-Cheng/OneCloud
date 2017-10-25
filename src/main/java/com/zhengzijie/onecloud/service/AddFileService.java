package com.zhengzijie.onecloud.service;

import com.alibaba.fastjson.JSONObject;
import com.zhengzijie.onecloud.dao.entity.LocalFileDO;

public interface AddFileService {
    JSONObject serve(long userID, boolean uploaded, String md5, LocalFileDO localFile);
}
