package com.zhengzijie.onecloud.service;

import com.alibaba.fastjson.JSONObject;

public interface RenameService {
    JSONObject serve(long id, String localName, String localType);
}
