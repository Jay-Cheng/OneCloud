package com.zhengzijie.onecloud.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.zhengzijie.onecloud.manager.util.JSONUtil;
import com.zhengzijie.onecloud.service.LoginService;
import com.zhengzijie.onecloud.service.dto.UserDTO;
import com.zhengzijie.onecloud.service.impl.LoginServiceImpl;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    JSONObject reqJSON = JSONUtil.getJSONObject(request.getReader());
	    
	    String account = reqJSON.getString("account");
	    String password = reqJSON.getString("password");
	    
	    LoginService loginService = new LoginServiceImpl();
	    JSONObject respJSON = loginService.serve(account, password);
	    
	    /* 设置本次会话的UserID */
	    UserDTO dto = (UserDTO) respJSON.get("data");
	    request.getSession().setAttribute("userID", dto.getId());
	    
        response.setContentType("application/json;charset=utf-8"); 
        PrintWriter writer = response.getWriter();
        writer.write(respJSON.toJSONString());
        writer.close();
	}

}
