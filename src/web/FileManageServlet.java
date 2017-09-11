package web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import dao.entity.LocalFileDO;
import manager.exception.DBQueryException;
import manager.util.JSONUtil;
import service.AddFileService;
import service.impl.AddFileServiceImpl;


@WebServlet("/FileManageServlet")
public class FileManageServlet extends HttpServlet {
    
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
        JSONObject reqJSON = JSONUtil.getJSONObject(request.getReader());
        JSONObject respJSON = new JSONObject();
        
        if (reqJSON.getString("addFile") != null && !reqJSON.getString("addFile").isEmpty()) {
            String md5 = reqJSON.getString("addFile");
            /* 文件是否刚刚上传 */
            boolean uploaded = reqJSON.getBooleanValue("uploaded");
            LocalFileDO localfile = reqJSON.toJavaObject(LocalFileDO.class);
            AddFileService service = new AddFileServiceImpl();
            try {
                /*
                 * stateCode
                 * 1：文件刚刚上传
                 * 2：文件不存在，需要上传
                 * 3：文件已存在，添加标记
                 * 4：数据库查询出错
                 * 5：文件和标记都已经存在（数据库数据保持不变）
                 */
                int stateCode = service.add(uploaded, md5, localfile);
                respJSON.put("state", stateCode);
                System.out.println("addFile-stateCode: " + stateCode);
                if (stateCode == 1 || stateCode == 3) {
                    respJSON.put("localFile", JSON.toJSON(service.getLocalFileDTO()));
                }
            } catch (DBQueryException e) {
                respJSON.put("state", 4); // 数据库查询出错
            } finally {
                response.setContentType("application/json");
                PrintWriter writer = response.getWriter();
                writer.write(respJSON.toJSONString());
                writer.close();
            }
        } 
	}

}
