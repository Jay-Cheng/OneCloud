package com.zhengzijie.onecloud.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhengzijie.onecloud.manager.constant.MenuConsts;
import com.zhengzijie.onecloud.service.DiskService;
import com.zhengzijie.onecloud.service.UserService;
import com.zhengzijie.onecloud.service.dto.UserDTO;

@RestController
@RequestMapping(value = "/api", produces = "application/json", consumes = "application/json")
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private DiskService diskService;
    
    /**
     * 根据用户名获取完整的用户信息
     */
    @RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
    public Map<String, Object> getUserInfo(@PathVariable String username) {
        UserDTO userDTO = userService.getUser(username);
        
        Map<String, Object> result = new HashMap<>();
        result.put("user", userDTO);
        return result;
    }
    
    /**
     * 获取用户不同菜单下的文件列表
     * 如：GET api/users/admin/photo，获取admin用户的所有照片
     */
    @RequestMapping(value = "/users/{username}/{menu}", method = RequestMethod.GET)
    public Map<String, Object> getMenu(
            @PathVariable String username, @PathVariable String menu) {
        /* 检查menu参数是否合法 */
        if (!MenuConsts.getConsts().contains(menu)) {
            throw new IllegalArgumentException("Illegal argument: " + menu);
        }
        long userID = userService.getUser(username).getId();
        Map<String, Object> result = diskService.getMenuContents(userID, menu);
        return result;
    }
    
    /**
     * 获取某个文件夹的内容
     * 如：GET api/users/admin/disk/folders/55?sort=1，获取admin用户网盘内folderID=55的文件夹内容，以最后修改时间排序
     */
    @RequestMapping(value = "/users/{username}/disk/folders/{folderID}", method = RequestMethod.GET)
    public Map<String, Object> getFolderContents(
            @PathVariable String username, @PathVariable Long folderID
            , @RequestParam(defaultValue = "0") int sort) {
        // TODO 参数校验
        long userID = userService.getUser(username).getId();
        Map<String, Object> result = diskService.getFolderContents(userID, folderID, sort);
        return result;
    }
    
    /**
     * 搜索用户网盘内的文件
     * 如：GET api/users/admin/disk/search?input=txt，搜索用户网盘内文件名含“txt”的文件及文件夹
     */
    @RequestMapping(value = "/users/{username}/disk/search", method = RequestMethod.GET)
    public Map<String, Object> search(@PathVariable String username
            , @RequestParam(required = true) String input) {
        long userID = userService.getUser(username).getId();
        Map<String, Object> result = diskService.search(userID, input);
        return result;
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Error> illegalArgument(IllegalArgumentException e) {
        Error error = new Error(e.getMessage());
        return new ResponseEntity<Error>(error, HttpStatus.BAD_REQUEST);
    }
}
