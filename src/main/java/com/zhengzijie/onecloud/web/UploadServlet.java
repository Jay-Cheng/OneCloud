package com.zhengzijie.onecloud.web;

import java.io.*;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@WebServlet("/UploadServlet")
@MultipartConfig
public class UploadServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    private static String filebase;
    
    @Override
    public void init() throws ServletException {
        filebase = getServletContext().getInitParameter("filebase");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        if (request.getParameter("delfile") != null && !request.getParameter("delfile").isEmpty()) {
            File file = new File(filebase + File.separator + request.getParameter("delfile"));
            if (file.exists()) {
                if (file.delete()) {
                    System.out.println("delfile:" + request.getParameter("delfile"));
                    // TODO 回复删除成功的信息，并在客户端进行相应操作
                }
            }
        } else if (request.getParameter("resfile") != null && !request.getParameter("resfile").isEmpty()) {
            File file = new File(filebase + File.separator + request.getParameter("resfile")); 
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
        /* 获取参数和文件 */
        Part filePart = request.getPart("files[]");
        String md5 = request.getParameter("md5");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // IE浏览器会提交路径而非文件名，因此要这样获得文件名
        System.out.println("uploading: " + fileName);
        /* 读写文件 */
        File file = new File(filebase + File.separator + md5);
        FileOutputStream fos = new FileOutputStream(file, true);
        BufferedInputStream fileContent = new BufferedInputStream(filePart.getInputStream());
        byte[] buf = new byte[1024];
        int hasRead;
        while ((hasRead = fileContent.read(buf)) != -1) {
            fos.write(buf, 0, hasRead);
        }
        fileContent.close();
        fos.close();
        /* 对客户端作出回复 */
        JSONArray arr = new JSONArray();
        JSONObject json = new JSONObject();
        json.put("files", arr);
        PrintWriter writer = response.getWriter();
        writer.write(json.toString());
        writer.close();
        
    }
}