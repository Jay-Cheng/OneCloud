package web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/GetFolderContentsServlet")
public class GetFolderContentsServlet extends HttpServlet {
    
	private static final long serialVersionUID = 1L;
       
    public GetFolderContentsServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    ServletContext context = request.getServletContext();
	    Connection conn = (Connection) context.getAttribute("DBConnection");
	    String userID = request.getParameter("userID");
	    String folderID = request.getParameter("folderID");
	    
	    	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
