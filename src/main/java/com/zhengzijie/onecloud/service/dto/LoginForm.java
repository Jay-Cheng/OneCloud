package com.zhengzijie.onecloud.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginForm {
    
    @NotNull @Size(min=5, max=16)
    private String username;
    
    @NotNull @Size(min=5, max=16)
    private String password;
    
    public LoginForm() {}
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String toString() {
        return "LoginForm [username=" + username + ", password=" + password + "]";
    }
}
