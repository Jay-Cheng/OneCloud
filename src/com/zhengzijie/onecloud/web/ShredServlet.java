package com.zhengzijie.onecloud.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhengzijie.onecloud.manager.util.JSONUtil;
import com.zhengzijie.onecloud.service.ShredMultipleService;
import com.zhengzijie.onecloud.service.impl.ShredMultipleServiceImpl;

@WebServlet("/ShredServlet")
public class ShredServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    JSONObject reqJSON = JSONUtil.getJSONObject(request.getReader());
	    
	    JSONArray dataArr = reqJSON.getJSONArray("shred");
	    long userID = (long) request.getSession().getAttribute("userID");
        
        ShredMultipleService shredMultipleService = new ShredMultipleServiceImpl();
        JSONObject result = shredMultipleService.serve(dataArr, userID);
        
        
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(result.toJSONString());
        writer.close();
	}
}
