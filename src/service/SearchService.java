package service;

import com.alibaba.fastjson.JSONObject;

public interface SearchService {
    JSONObject serve(long userID, String input);
}
