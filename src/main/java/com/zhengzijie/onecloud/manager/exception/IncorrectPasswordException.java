package com.zhengzijie.onecloud.manager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.UNAUTHORIZED, reason="Incorrect Password")
public class IncorrectPasswordException extends Exception {

    private static final long serialVersionUID = 1L;

}
