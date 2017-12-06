package com.zhengzijie.onecloud.manager;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppWideExceptionHandler {
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Error> illegalArgument(IllegalArgumentException e) {
        Error error = new Error(e.getMessage());
        return new ResponseEntity<Error>(error, HttpStatus.BAD_REQUEST);
    }
}
