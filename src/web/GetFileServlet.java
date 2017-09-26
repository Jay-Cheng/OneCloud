package web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import service.GetFileService;
import service.impl.GetFileServiceImpl;

@WebServlet("/GetFileServlet")
public class GetFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String type = request.getParameter("type");
	    long userID = (long) request.getSession().getAttribute("userID");
	    
	    GetFileService getFileService = new GetFileServiceImpl();
	    JSONObject respJSON = getFileService.serve(userID, type);
	    
        response.setContentType("application/json;charset=utf-8"); 
        PrintWriter writer = response.getWriter();
        writer.write(respJSON.toJSONString());
        writer.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
