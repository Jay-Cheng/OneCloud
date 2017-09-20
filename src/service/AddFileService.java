package service;

import com.alibaba.fastjson.JSONObject;

import dao.entity.LocalFileDO;

public interface AddFileService {
    JSONObject serve(long userID, boolean uploaded, String md5, LocalFileDO localFile);
}
