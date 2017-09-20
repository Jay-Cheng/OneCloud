package web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import dao.entity.LocalFolderDO;
import manager.util.JSONUtil;
import service.AddFolderService;
import service.impl.AddFolderServiceImpl;

@WebServlet("/AddFolderServlet")
public class AddFolderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    JSONObject reqJSON = JSONUtil.getJSONObject(request.getReader());
	    
	    LocalFolderDO folder = reqJSON.toJavaObject(LocalFolderDO.class);
	    folder.setUserID((Long)request.getSession().getAttribute("userID"));
	    
	    AddFolderService addFolderService = new AddFolderServiceImpl();
	    JSONObject respJSON = addFolderService.serve(folder);
	    
	    
	    response.setContentType("application/json;charset=utf-8"); 
	    PrintWriter writer = response.getWriter();
	    writer.write(respJSON.toJSONString());
	    writer.close();
	}

}
