package com.zhengzijie.onecloud.service;

import com.alibaba.fastjson.JSONObject;

public interface MoveService {
    JSONObject serve(Long id, Long moveTo);
}
