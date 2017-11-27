package com.zhengzijie.onecloud.web;

import java.util.HashMap;
import java.util.Map;

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
import com.zhengzijie.onecloud.web.dto.UserDTO;

@RestController
@RequestMapping("/api")
public class LoginController {
    
    @Autowired
    private UserService userService;
    
    @RequestMapping(value = "/authentication", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public Map<String, Object> authentication(@RequestBody LoginForm loginForm) throws NoSuchUserException, IncorrectPasswordException {
        System.out.println(loginForm.getUsername() + " " + loginForm.getPassword());
        Map<String, Object> result = new HashMap<>();
        UserDTO userDTO = userService.login(loginForm.getUsername(), loginForm.getPassword());
        result.put("user", userDTO);
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
