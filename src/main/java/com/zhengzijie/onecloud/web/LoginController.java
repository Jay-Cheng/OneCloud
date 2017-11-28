package com.zhengzijie.onecloud.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zhengzijie.onecloud.manager.exception.IncorrectPasswordException;
import com.zhengzijie.onecloud.manager.exception.NoSuchUserException;
import com.zhengzijie.onecloud.service.UserService;
import com.zhengzijie.onecloud.web.dto.LoginForm;

@RestController
@RequestMapping(value = "/api", produces = "application/json", consumes = "application/json")
public class LoginController {
    
    @Autowired
    private UserService userService;
    
    /**
     * POST api/authentication 
     * 检查用户名与密码，若用户名与密码匹配，返回一个该用户的Token，
     * 该Token可用于访问该用户的资源
     */
    @RequestMapping(value = "/authentication", method = RequestMethod.POST)
    public Map<String, Object> authentication(@RequestBody @Valid LoginForm loginForm) throws NoSuchUserException, IncorrectPasswordException {
        System.out.println(loginForm);
        Map<String, Object> result = new HashMap<>();
        String token = userService.login(loginForm.getUsername(), loginForm.getPassword());
        result.put("token", token);
        return result;
    }
    
    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity<Error> noSuchUser(NoSuchUserException e) {
        Error error = new Error("username not fonund");
        return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<Error> incorrectPassword(IncorrectPasswordException e) {
        Error error = new Error("incorrect password");
        return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);
    }
}
