package com.zhengzijie.onecloud.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zhengzijie.onecloud.manager.JWTUtil;

import io.jsonwebtoken.ExpiredJwtException;

public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    
    /** 受保护资源的根路径 */
    private static final String API_V1_URI_ROOT = "/OneCloud/api/v1/users/";
    /** Token的类型 */
    private static final String TOKEN_TYPE_BEARER = "Bearer";
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String token = null;
        if ((token = request.getParameter("token")) == null) {
            token = request.getHeader("Authorization");
        }
        
        if (token == null) {
            String message = "An access token is required to request this resource";
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, message);
            return false;
        }
        
        String username;
        try {
            String[] tokenComponent = token.split(" ");
            if (!tokenComponent[0].equals(TOKEN_TYPE_BEARER)) {
                throw new IllegalArgumentException("Unsupported token type");
            }
            username = JWTUtil.parseToken(tokenComponent[1]);
        } catch (ExpiredJwtException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return false;
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return false;
        }
        if (request.getRequestURI().matches(API_V1_URI_ROOT + username + ".*")) {
            return true;
        } else {
            String message = "Token does not match";
            response.sendError(HttpServletResponse.SC_FORBIDDEN, message);
            return false;
        }
    }
}
