package com.zhengzijie.onecloud.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.zhengzijie.onecloud.service.SearchService;
import com.zhengzijie.onecloud.service.impl.SearchServiceImpl;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String input = request.getParameter("input");
	    long userID = (long) request.getSession().getAttribute("userID");
	    
	    SearchService searchService = new SearchServiceImpl();
	    JSONObject respJSON = searchService.serve(userID, input);
	    
        response.setContentType("application/json;charset=utf-8"); 
        PrintWriter writer = response.getWriter();
        writer.write(respJSON.toJSONString());
        writer.close();
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
