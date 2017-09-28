package web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import service.ReadFolderService;
import service.impl.ReadFolderServiceImpl;


@WebServlet("/EnterFolderServlet")
public class EnterFolderServlet extends HttpServlet {
    
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    long userID = (long) request.getSession().getAttribute("userID");
	    long folderID = Long.parseLong(request.getParameter("folderID"));
	    int sortType = Integer.parseInt(request.getParameter("sortType"));
	    
	    ReadFolderService readFolderService = new ReadFolderServiceImpl();	    
	    JSONObject respJSON = readFolderService.serve(userID, folderID, sortType);
	    
        response.setContentType("application/json;charset=utf-8"); 
        PrintWriter writer = response.getWriter();
        writer.write(respJSON.toJSONString());
        writer.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
