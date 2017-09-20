package web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import manager.util.JSONUtil;
import service.RenameService;
import service.factory.RenameServiceFactory;

@WebServlet("/RenameServlet")
public class RenameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    doPost(request, response);
	}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    JSONObject reqJSON = JSONUtil.getJSONObject(request.getReader());
	    
	    long id = reqJSON.getLongValue("id");
	    String localName = reqJSON.getString("localName");
	    String localType = reqJSON.getString("localType");
	    String type = reqJSON.getString("type");// 文件or文件夹
	    
        RenameService renameService = RenameServiceFactory.getService(type);
        JSONObject respJSON = renameService.serve(id, localName, localType);
	    
	    response.setContentType("application/json;charset=utf-8");
	    PrintWriter writer = response.getWriter();
	    writer.write(respJSON.toJSONString());
	    writer.close();
	}

}
