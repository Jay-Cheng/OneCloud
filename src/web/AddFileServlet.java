package web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import dao.entity.LocalFileDO;
import manager.util.JSONUtil;
import service.AddFileService;
import service.impl.AddFileServiceImpl;


@WebServlet("/AddFileServlet")
public class AddFileServlet extends HttpServlet {
    
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject reqJSON = JSONUtil.getJSONObject(request.getReader());
        
        String filebase = request.getServletContext().getInitParameter("filebase");
        long userID = (long) request.getSession().getAttribute("userID");
        boolean uploaded = reqJSON.getBooleanValue("uploaded");// 文件是否刚刚上传
        String md5 = reqJSON.getString("addFile");
        LocalFileDO localfile = reqJSON.toJavaObject(LocalFileDO.class);
        
        AddFileService addFileService = new AddFileServiceImpl(filebase);
        JSONObject respJSON = addFileService.serve(userID, uploaded, md5, localfile);
        
        response.setContentType("application/json;charset=utf-8"); 
        PrintWriter writer = response.getWriter();
        writer.write(respJSON.toJSONString());
        writer.close();
	}

}
