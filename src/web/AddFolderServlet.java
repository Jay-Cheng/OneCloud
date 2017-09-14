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
	    JSONObject respJSON = new JSONObject();
	    
	    boolean isSuccess = false;
	    LocalFolderDO folder = reqJSON.toJavaObject(LocalFolderDO.class);
	    folder.setUserID((Long)request.getSession().getAttribute("userID"));
	    
	    AddFolderService addFolderService = new AddFolderServiceImpl();
	    addFolderService.serve(folder);
	    
	    if (folder != null) {
	        isSuccess = true;
	    }
	    
	    respJSON.put("isSuccess", isSuccess);
	    if (isSuccess) {
	        respJSON.put("id", folder.getId());
	        respJSON.put("ldtModified", folder.getLdtModified());
	    }
	    
	    response.setContentType("application/json;charset=utf-8"); 
	    PrintWriter writer = response.getWriter();
	    writer.write(respJSON.toJSONString());
	    writer.close();
	}

}
