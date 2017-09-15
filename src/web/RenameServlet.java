package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import dao.entity.LocalFileDO;
import dao.entity.LocalFolderDO;
import manager.util.JSONUtil;
import service.RenameService;
import service.impl.RenameFileServiceImpl;
import service.impl.RenameFolderServiceImpl;

@WebServlet("/RenameServlet")
public class RenameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    doPost(request, response);
	}

	@SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    JSONObject reqJSON = JSONUtil.getJSONObject(request.getReader());
	    JSONObject respJSON = new JSONObject();
	    
        @SuppressWarnings("rawtypes")
        RenameService service;
        boolean isSuccess = false;
        LocalDateTime ldtModified = null;
	    if (reqJSON.getString("localType") == null) {
	        service = new RenameFolderServiceImpl();
	        ldtModified = service.rename(reqJSON.toJavaObject(LocalFolderDO.class));
	    } else {
	        service = new RenameFileServiceImpl();
	        ldtModified = service.rename(reqJSON.toJavaObject(LocalFileDO.class));
	    }
	    if (ldtModified != null) {
	        isSuccess = true;
	    }
	    
	    respJSON.put("isSuccess", isSuccess);
	    respJSON.put("ldt_modified", ldtModified);
	    response.setContentType("application/json;charset=utf-8");
	    PrintWriter writer = response.getWriter();
	    writer.write(respJSON.toJSONString());
	    writer.close();
	}

}
