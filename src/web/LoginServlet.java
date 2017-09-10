package web;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import dao.entity.UserDO;
import manager.exception.DBQueryException;
import manager.exception.UserNotFoundException;
import service.LoginService;
import service.impl.LoginServiceImpl;
import web.dto.UserDTO;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    /* 获取并解析JSON */
        StringBuilder json = new StringBuilder();
        String temp = null;
        BufferedReader reader = request.getReader();
        while ((temp = reader.readLine()) != null) {
            json.append(temp);
        }
	    /* 只有account和password域有效 */
	    UserDO user = JSONObject.parseObject(json.toString(), UserDO.class);
	    
	    LoginService loginService = new LoginServiceImpl();
	    JSONObject responseJson = new JSONObject();
	    
	    try {
            if (loginService.checkPassword(user.getAccount(), user.getPassword())) {
                UserDTO dto = loginService.getUserDTO();
                
                responseJson.put("state", 1);// 状态1表示登录成功
                responseJson.put("user", JSONObject.toJSON(dto));
            } else {
                responseJson.put("state", 2);// 状态2表示密码错误
            }
        } catch (UserNotFoundException e) {
            responseJson.put("state", 3);// 状态3表示账户不存在
        } catch (DBQueryException e) {
            responseJson.put("state", 4);// 状态4表示数据库查询出现问题
        }
	    
        response.setContentType("application/json");
        response.getWriter().write(responseJson.toJSONString());
	}

}
