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
import service.ShredService;
import service.factory.ShredServiceFactory;

@WebServlet("/ShredServlet")
public class ShredServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject reqJSON = JSONUtil.getJSONObject(request.getReader());
        JSONObject respJSON = new JSONObject();
        
        long id = reqJSON.getLongValue("id");
        String type = reqJSON.getString("type");
        
        ShredService service = new ShredServiceFactory().getService(type);
        boolean isSuccess = service.shred(id);
        
        respJSON.put("isSuccess", isSuccess);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(respJSON.toJSONString());
        writer.close();
	}

}
