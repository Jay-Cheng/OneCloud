package servlet;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import dao.UserDAO;
import po.User;

/**
 * Servlet implementation class doLoginServlet
 */
@WebServlet("/doLoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    StringBuffer jsonString = new StringBuffer();
	    String temp = null;
	    try {
	      BufferedReader reader = request.getReader();
	      while ((temp = reader.readLine()) != null) {
	          jsonString.append(temp);
	      }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    User user = JSONObject.parseObject(jsonString.toString(), User.class);
	    System.out.println(user.getUsername());
        UserDAO userdao = new UserDAO();
        if (userdao.check(user)) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write("success");
        } else {
            // do nothing
        }
	}

}
