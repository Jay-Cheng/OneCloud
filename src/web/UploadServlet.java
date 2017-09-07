package web;

import java.io.*;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        if (request.getParameter("delfile") != null && !request.getParameter("delfile").isEmpty()) {
            File file = new File(request.getServletContext().getRealPath("/WEB-INF/upload") + "/" + request.getParameter("delfile"));
            if (file.exists()) {
                if (file.delete()) {
                    System.out.println("delfile:" + request.getParameter("delfile"));
                    // TODO 回复删除成功的信息，并在客户端进行相应操作
                }
            }
        } else if (request.getParameter("resfile") != null && !request.getParameter("resfile").isEmpty()) {
            String filename = request.getParameter("resfile");
            File file = new File(request.getServletContext().getRealPath("/WEB-INF/upload") + "/" + filename); 
            if (file.exists()) {
                Long uploadedBytes = file.length();
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write(uploadedBytes.toString());
            }
        } else {
            PrintWriter writer = response.getWriter();
            writer.write("call POST with multipart form data");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new IllegalArgumentException("Request is not multipart, please 'multipart/form-data' enctype for your form.");
        }

        ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
        uploadHandler.setHeaderEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        JSONArray temp = new JSONArray();
        JSONObject json = new JSONObject();
        try {
            List<FileItem> items = uploadHandler.parseRequest(request);
            for (FileItem item : items) {
                if (!item.isFormField()) {
                        File file = new File(request.getServletContext().getRealPath("/WEB-INF/upload")+"/", item.getName());
                        FileOutputStream fos = new FileOutputStream(file, true);
                        BufferedInputStream bis = new BufferedInputStream(item.getInputStream());
                        int hasRead;
                        byte[] buf = new byte[1024];
                        while ((hasRead = bis.read(buf)) != -1) {
                            fos.write(buf, 0, hasRead);
                        }
                        fos.close();
                        bis.close();
                }
            }
        } catch(FileUploadException e) {
            /* 处理客户端取消文件上传时抛出的EOFException */
            if (e.getCause().getClass().equals(EOFException.class)) {
                System.out.println("client cancel a file upload mission");
            } else {
                throw new RuntimeException(e);
            }
        } catch(Exception e) {
            throw new RuntimeException(e);
        } finally {
            json.put("files", temp);
            writer.write(json.toString());
            writer.close();
        }
    }
}