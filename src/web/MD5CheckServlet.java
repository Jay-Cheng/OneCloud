package web;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import dao.entity.LocalFileDO;
import manager.exception.DBQueryException;
import service.MD5CheckService;
import service.impl.MD5CheckServiceImpl;


@WebServlet("/MD5CheckServlet")
public class MD5CheckServlet extends HttpServlet {
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
	    JSONObject obj = JSON.parseObject(json.toString());
	    String md5 = obj.getString("md5");
	    LocalFileDO localfile = obj.toJavaObject(LocalFileDO.class);
	    
	    MD5CheckService service = new MD5CheckServiceImpl();
	    JSONObject state = new JSONObject();
	    try {
            if (service.check(md5, localfile)) {
                state.put("state", 1);
            } else {
                state.put("state", 2);
            }
        } catch (DBQueryException e) {
            state.put("state", 3);
        } finally {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(state.toJSONString());
        }
	}

}
