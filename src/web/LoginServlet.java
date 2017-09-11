package web;

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
import manager.util.JSONUtil;
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
	    String jsonString = JSONUtil.getJSONString(request.getReader());
	    /* 只有account和password域有效 */
	    UserDO user = JSONObject.parseObject(jsonString.toString(), UserDO.class);
	    
	    LoginService loginService = new LoginServiceImpl();
	    JSONObject respJSON = new JSONObject();
	    
	    try {
            if (loginService.checkPassword(user.getAccount(), user.getPassword())) {
                UserDTO dto = loginService.getUserDTO();
                
                respJSON.put("state", 1);// 状态1表示登录成功
                respJSON.put("user", JSONObject.toJSON(dto));
            } else {
                respJSON.put("state", 2);// 状态2表示密码错误
            }
        } catch (UserNotFoundException e) {
            respJSON.put("state", 3);// 状态3表示账户不存在
        } catch (DBQueryException e) {
            respJSON.put("state", 4);// 状态4表示数据库查询出现问题
        }
	    
        response.setContentType("application/json");
        response.getWriter().write(respJSON.toJSONString());
	}

}
