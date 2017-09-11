package web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import manager.exception.DBQueryException;
import service.GetFolderContentsService;
import service.impl.GetFolderContentsServiceImpl;
import web.dto.LocalFileDTO;
import web.dto.LocalFolderDTO;


@WebServlet("/GetFolderContentsServlet")
public class GetFolderContentsServlet extends HttpServlet {
    
	private static final long serialVersionUID = 1L;
	
    public GetFolderContentsServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    long userID = Long.parseLong(request.getParameter("userID"));
	    long folderID = Long.parseLong(request.getParameter("folderID"));
	    
	    GetFolderContentsService service = new GetFolderContentsServiceImpl();	    
	    List<LocalFolderDTO> folders = service.getChildFolders(userID, folderID);
	    List<LocalFileDTO> files;
        try {
            files = service.getChildFiles(userID, folderID);
        } catch (DBQueryException e) {
            files = new ArrayList<>();
            e.printStackTrace();
        }
	    
	    JSONObject result = new JSONObject();
	    result.put("folders", JSON.toJSON(folders));
	    result.put("files", JSON.toJSON(files));
	    
	    response.setContentType("application/json;charset=utf-8");
	    response.getWriter().write(result.toJSONString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
