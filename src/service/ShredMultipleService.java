package service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface ShredMultipleService {
    JSONObject serve(JSONArray data, long userID);
}
