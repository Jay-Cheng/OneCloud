package com.zhengzijie.onecloud.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.zhengzijie.onecloud.dao.entity.FileDO;
import com.zhengzijie.onecloud.dao.entity.LocalFileDO;
import com.zhengzijie.onecloud.manager.validation.ParamChecker;
import com.zhengzijie.onecloud.service.DownloadService;
import com.zhengzijie.onecloud.service.UploadService;
import com.zhengzijie.onecloud.web.reqbody.UploadReqBody;

@RestController 
@RequestMapping(value = "/api/v1")
public class FileController {
    @Autowired
    private UploadService uploadService;
    @Autowired
    private DownloadService downloadService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ParamChecker paramChecker;
    /** 分块文件的大小，与前端保持一致 */
    private static final long MAX_CHUNK_SIZE = 102400;
    /**
     * 功能：接收分块上传的文件块
     * 示例：POST api/v1/users/admin/disk/files
     */
    @RequestMapping(value="/users/{username}/disk/files", method = RequestMethod.POST)
    public Map<String, Object> upload(HttpServletRequest req, 
            @RequestPart("files[]") Part part
            , @Valid UploadReqBody reqbody) throws IOException {
        // TODO 参数校验
        System.out.println(req.getHeader("content-range"));
        String contentRange = req.getHeader("content-range");
        
        /* 检查云盘存储空间是否足够 */
        long size;
        if (contentRange != null) {
            size = Long.parseLong(contentRange.split("/")[1]);
        } else {
            size = part.getSize();
        }
        if (!paramChecker.isUserStorageEnough(reqbody.getUserID(), size)) {
            throw new IllegalArgumentException("Not enough storage to upload this file");
        }
        
        /* 处理没有Content-Range请求头的小文件 */
        if (contentRange == null && part.getSize() <= MAX_CHUNK_SIZE) {
            LocalFileDO localFile = modelMapper.map(reqbody, LocalFileDO.class);
            return uploadService.serveSmallFile(part, reqbody.getMd5(), localFile);
        }
        
        if (isFirstPart(contentRange)) {
            LocalFileDO localFile = modelMapper.map(reqbody, LocalFileDO.class);
            return uploadService.serveFirstPart(part, reqbody.getMd5(), localFile);
        } else if (isLastPart(contentRange)) {
            FileDO file = new FileDO();
            file.setMd5(reqbody.getMd5());
            file.setType(part.getHeader("content-type"));
            file.setSize(size);
            LocalFileDO localFile = modelMapper.map(reqbody, LocalFileDO.class);
            uploadService.savePart(part, reqbody.getMd5());
            return uploadService.serveLastPart(localFile, file);
        } else {
            uploadService.savePart(part, reqbody.getMd5());
            return null;
        }
    }
    
    /**
     * 功能：取消上传<br />
     * 示例：DELETE api/v1/users/admin/disk/files?cancel=abcd，取消上传md5值为“abcd”的文件
     */
    @RequestMapping(value="/users/{username}/disk/files", method = RequestMethod.DELETE, params="cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelUpload(@RequestParam String cancel) throws InterruptedException {
        // TODO 参数校验
        uploadService.cancel(cancel);
    }
    
    /**
     * 功能：获取上传到一半的文件断点<br />
     * 示例：GET api/v1/users/admin/disk/files?resume=abcd，获取md5值为“abcd”的文件的断点
     */
    @RequestMapping(value="/users/{username}/disk/files", method = RequestMethod.GET, params="resume")
    public Long resumeUpload(@RequestParam String resume) {
        // TODO 参数校验
        return uploadService.resmue(resume);
    }
    
    /**
     * 功能：下载<br />
     * 示例：GET api/v1/users/admin/disk/files?files=1,2&folders=3,4，打包下载ID=1,2的文件和ID=3,4的文件夹
     */
    @RequestMapping(value="/users/{username}/disk/files", method = RequestMethod.GET, params = {"files", "folders"})
    public void download(@RequestParam List<Long> files, @RequestParam List<Long> folders
            , HttpServletResponse response) throws IOException {
        // TODO 参数校验
        if (files.size() + folders.size() == 0) {
            throw new IllegalArgumentException("params must contain at least one file or folder");
        }
        
        String filename = downloadService.generateZipFilename(files, folders);
        filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
        response.setContentType("application/octet-stream;");
        response.setHeader("Content-disposition","attachment; filename=" + filename);
        
        downloadService.download(files, folders, response.getOutputStream());
    }
    
    
    /**
     * 根据Content-Range请求头(如：bytes 0-9999/312329)，判断文件块是否是首个块（chunk）
     */
    private boolean isFirstPart(String contentRange) {
        return contentRange.charAt(contentRange.indexOf(' ') + 1) == '0';
    }
    
    /**
     * 根据Content-Range请求头(如：bytes 310000-312328/312329)，判断文件块是否是最后一个块（chunk）
     */
    private boolean isLastPart(String contentRange) {
        String[] validPart = contentRange.substring(contentRange.indexOf('-') + 1).split("/");
        int current = Integer.parseInt(validPart[0]);
        int size = Integer.parseInt(validPart[1]);
        return size - 1 == current;
    }
}
