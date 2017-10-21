package com.zhengzijie.onecloud.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.zhengzijie.onecloud.service.DownloadService;
import com.zhengzijie.onecloud.service.impl.DownloadServiceImpl;

@WebServlet("/DownloadServlet")
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String jsonString = request.getParameter("dl");
	    JSONArray jsonArray = JSON.parseArray(jsonString);
	    
	    DownloadService downloader = new DownloadServiceImpl();
	    downloader.init(jsonArray, request.getServletContext().getInitParameter("filebase"));
	    
	    String filename = downloader.getFilename();
	    filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
	    response.setContentType("application/octet-stream;");
	    response.setHeader("Content-disposition","attachment; filename=" + filename);
	    
	    try {
	        downloader.serve(response.getOutputStream());
	    } catch (Exception e) {
	        e.printStackTrace();
	        PrintWriter writer = response.getWriter();
	        writer.write("downloading encounter a problem");
	        writer.close();
	    }

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    doGet(request, response);
	}
}
