package com.zhengzijie.onecloud.manager.util;

import java.io.BufferedReader;
import java.io.IOException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


public class JSONUtil {
    
    public static String getJSONString(BufferedReader reader) {
        
        StringBuilder json = new StringBuilder();
        String temp = null;
        try {
            while ((temp = reader.readLine()) != null) {
                json.append(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json.toString();
        
    }
    
    public static JSONObject getJSONObject(BufferedReader reader) {
        
        String jsonString = getJSONString(reader);
        return JSON.parseObject(jsonString);
    }
}
