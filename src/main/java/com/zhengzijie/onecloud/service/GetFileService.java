package com.zhengzijie.onecloud.service;

import com.alibaba.fastjson.JSONObject;

public interface GetFileService {
    JSONObject serve(long userID, String type);
}
