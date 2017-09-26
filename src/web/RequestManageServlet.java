package web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RequestManageServlet")
public class RequestManageServlet extends HttpServlet {
    
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String action = request.getParameter("action");
	    System.out.println(action);
        switch (action) {
        case "login":
            request.getRequestDispatcher("LoginServlet").forward(request, response);break;
        case "enterFolder":
            request.getRequestDispatcher("EnterFolderServlet").forward(request, response);break;
        case "addFile":
            request.getRequestDispatcher("AddFileServlet").forward(request, response);break;
        case "addFolder":
            request.getRequestDispatcher("AddFolderServlet").forward(request, response);break;
        case "rename":
            request.getRequestDispatcher("RenameServlet").forward(request, response);break;
        case "move":
            request.getRequestDispatcher("MoveServlet").forward(request, response);break;
        case "shred":
            request.getRequestDispatcher("ShredServlet").forward(request, response);break;
        case "download":
            request.getRequestDispatcher("DownloadServlet").forward(request, response);break;
        case "getFile":
            request.getRequestDispatcher("GetFileServlet").forward(request, response);break;
        default:
            /* 使用异步请求时不能在服务端重定向 */
            response.getWriter().write("error:action doesn't match");
        }
	}

}
