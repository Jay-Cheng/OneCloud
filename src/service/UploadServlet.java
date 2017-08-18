package service;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.imgscalr.Scalr;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("getfile") != null && !request.getParameter("getfile").isEmpty()) {
            System.out.println("getfile:" + request.getParameter("getfile"));
            File file = new File(request.getServletContext().getRealPath("/WEB-INF/upload") + "/" + request.getParameter("getfile"));
            if (file.exists()) {
                int bytes = 0;
                ServletOutputStream op = response.getOutputStream();
                response.setCharacterEncoding("UTF-8");
                response.setContentType(getMimeType(file));
                response.setContentLength((int) file.length());
                response.setHeader( "Content-Disposition", "attachment; filename=\"" + file.getName() + "\"" );

                byte[] bbuf = new byte[1024];
                DataInputStream in = new DataInputStream(new FileInputStream(file));

                while ((in != null) && ((bytes = in.read(bbuf)) != -1)) {
                    op.write(bbuf, 0, bytes);
                }

                in.close();
                op.flush();
                op.close();
            }
        } else if (request.getParameter("delfile") != null && !request.getParameter("delfile").isEmpty()) {
            File file = new File(request.getServletContext().getRealPath("/WEB-INF/upload") + "/" + request.getParameter("delfile"));
            if (file.exists()) {
                if (file.delete()) {
                    System.out.println("delfile:" + request.getParameter("delfile"));            
                    try {
                        JSONObject json = new JSONObject();
                        json.put(request.getParameter("delfile").toString(), true);
                        JSONArray temp = new JSONArray();
                        temp.add(json);
                        JSONObject finalJson = new JSONObject();
                        finalJson.put("files", temp);
                        response.getWriter().write(finalJson.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }          
                }
            }
        } else if (request.getParameter("getthumb") != null && !request.getParameter("getthumb").isEmpty()) {
            System.out.println("getthumb:" + request.getParameter("getthumb"));
            File file = new File(request.getServletContext().getRealPath("/WEB-INF/upload") + "/" + request.getParameter("getthumb"));
                if (file.exists()) {
                    String mimetype = getMimeType(file);
                    if (mimetype.endsWith("png") || mimetype.endsWith("jpeg")|| mimetype.endsWith("jpg") || mimetype.endsWith("gif")) {
                        BufferedImage im = ImageIO.read(file);
                        if (im != null) {
                            BufferedImage thumb = Scalr.resize(im, 75); 
                            ByteArrayOutputStream os = new ByteArrayOutputStream();
                            if (mimetype.endsWith("png")) {
                                ImageIO.write(thumb, "PNG" , os);
                                response.setContentType("image/png");
                            } else if (mimetype.endsWith("jpeg")) {
                                ImageIO.write(thumb, "jpg" , os);
                                response.setContentType("image/jpeg");
                            } else if (mimetype.endsWith("jpg")) {
                                ImageIO.write(thumb, "jpg" , os);
                                response.setContentType("image/jpeg");
                            } else {
                                ImageIO.write(thumb, "GIF" , os);
                                response.setContentType("image/gif");
                            }
                            ServletOutputStream srvos = response.getOutputStream();
                            response.setContentLength(os.size());
                            response.setHeader( "Content-Disposition", "inline; filename=\"" + file.getName() + "\"" );
                            os.writeTo(srvos);
                            srvos.flush();
                            srvos.close();
                        }
                    }
            } // TODO: check and report success
        } else if (request.getParameter("resfile") != null && !request.getParameter("resfile").isEmpty()) {
            String filename = request.getParameter("resfile");
            File file = new File(request.getServletContext().getRealPath("/WEB-INF/upload") + "/" + filename); 
            if (file.exists()) {
                Long uploadedBytes = file.length();
                System.out.println("uploadedBytes: " + uploadedBytes);
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write(uploadedBytes.toString());
            }
        } else {
            System.err.println("wtf?");
            PrintWriter writer = response.getWriter();
            writer.write("call POST with multipart form data");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        System.out.println(request.getHeader("Content-Range"));
        /*System.out.println(request.getHeader("Content-Disposition"));*/
        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new IllegalArgumentException("Request is not multipart, please 'multipart/form-data' enctype for your form.");
        }

        ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
        uploadHandler.setHeaderEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
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
                        JSONObject jsono = new JSONObject();
                        jsono.put("name", item.getName());
                        jsono.put("size", item.getSize());
                        jsono.put("url", "copyedUploadServlet?getfile=" + item.getName());
                        jsono.put("thumbnailUrl", "copyedUploadServlet?getthumb=" + item.getName());
                        jsono.put("deleteUrl", "copyedUploadServlet?delfile=" + item.getName());
                        jsono.put("deleteType", "GET");
                        temp.add(jsono);
                }
            }
            json.put("files", temp);
            /*System.out.println(json);*/
        } catch(FileUploadException e) {
            /* 处理客户端取消文件上传时抛出的EOFException */
            if (e.getCause().getClass().equals(EOFException.class)) {
                System.out.println("client cancel a file upload");
            } else {
                throw new RuntimeException(e);
            }
        } catch(Exception e) {
            throw new RuntimeException(e);
        } finally {
            writer.write(json.toString());
            writer.close();
        }

    }

    private String getMimeType(File file) {
        String mimetype = "";
        if (file.exists()) {
            if (getSuffix(file.getName()).equalsIgnoreCase("png")) {
                mimetype = "image/png";
            }else if(getSuffix(file.getName()).equalsIgnoreCase("jpg")){
                mimetype = "image/jpg";
            }else if(getSuffix(file.getName()).equalsIgnoreCase("jpeg")){
                mimetype = "image/jpeg";
            }else if(getSuffix(file.getName()).equalsIgnoreCase("gif")){
                mimetype = "image/gif";
            }else {
                javax.activation.MimetypesFileTypeMap mtMap = new javax.activation.MimetypesFileTypeMap();
                mimetype  = mtMap.getContentType(file);
            }
        }
        return mimetype;
    }



    private String getSuffix(String filename) {
        String suffix = "";
        int pos = filename.lastIndexOf('.');
        if (pos > 0 && pos < filename.length() - 1) {
            suffix = filename.substring(pos + 1);
        }
        return suffix;
    }
}