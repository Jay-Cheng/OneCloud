package com.zhengzijie.onecloud.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zhengzijie.onecloud.manager.JWTUtil;

import io.jsonwebtoken.ExpiredJwtException;


@SuppressWarnings("unused")
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {
    
    private String rootURL = "/OneCloud/api/";
    
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//            throws Exception {
//        System.out.println(request.getRequestURI());
//        if (request.getRequestURI().matches(rootURL + "authentication")) {
//            return true;
//        }
//        String token = request.getHeader("jwt");
//        if (token == null || token.isEmpty()) {
//            response.sendError(HttpServletResponse.SC_FORBIDDEN);
//            return false;
//        }
//        String username;
//        try {
//            username = JWTUtil.parseToken(token);
//        } catch (ExpiredJwtException e) {
//            response.sendError(HttpServletResponse.SC_FORBIDDEN);
//            return false;
//        }
//        if (request.getRequestURI().matches(rootURL + username)) {
//            return true;
//        } else {
//            response.sendError(HttpServletResponse.SC_FORBIDDEN);
//            return false;
//        }
//    }
    
}
