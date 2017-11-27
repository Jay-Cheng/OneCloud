package com.zhengzijie.onecloud.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class ViewController {
    
    @RequestMapping(method=RequestMethod.GET)
    public String getLoginView() {
        return "login";
    }
    
    @RequestMapping(value="/{user}",method=RequestMethod.GET)
    public String getHomeView() {
        return "home";
    }
}
