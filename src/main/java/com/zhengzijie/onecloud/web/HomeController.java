package com.zhengzijie.onecloud.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zhengzijie.onecloud.service.UserService;
import com.zhengzijie.onecloud.web.dto.UserDTO;

@RestController
@RequestMapping(value = "/api", produces = "application/json", consumes = "application/json")
public class HomeController {
    @Autowired
    private UserService userService;
    
    /**
     * GET api/users/{username} 
     * 根据用户名获取完整的用户信息
     */
    @RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
    public Map<String, Object> getUserInfo(@PathVariable String username) {
        Map<String, Object> result = new HashMap<>();
        
        UserDTO userDTO = userService.getUser(username);
        result.put("user", userDTO);
        
        return result;
    }
    
    @RequestMapping(value = "/users/{username}/{menu}", method = RequestMethod.GET)
    public Map<String, Object> getMenu(
            @PathVariable String username, @PathVariable String menu) {
        Map<String, Object> result = new HashMap<>();
        return result;
    }
    
    @RequestMapping(value = "/users/{username}/disk/folders/{folderID}", method = RequestMethod.GET)
    public Map<String, Object> getFolderContents(
            @PathVariable String username, @PathVariable Long folderID) {
        Map<String, Object> result = new HashMap<>();
        
        return result;
    }
    
}
