package web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class SessionFilter implements Filter {
    
    private final String redirect = "login.html";
    
    @Override
    public void init(FilterConfig arg0) throws ServletException {}
    
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	    HttpServletRequest httpReq = (HttpServletRequest) request;
	    HttpServletResponse httpResp = (HttpServletResponse) response;
	    
	    if (isAjaxRequest(httpReq)) {
	        // 如果请求是登录则不过滤
	        if ("login".equalsIgnoreCase(httpReq.getParameter("action"))) {
	            // do nothing
	        } else if (httpReq.getSession().getAttribute("userID") == null) {
	            httpResp.setHeader("sessionStatus","timeout");
	        }
	    } else {
	        /* 不进行过滤的资源 */
	        if (httpReq.getServletPath().endsWith("login.html") || httpReq.getServletPath().endsWith(".css") || httpReq.getServletPath().endsWith(".js") || httpReq.getServletPath().endsWith(".png")) {
	            // do nothing
	        } else {	            
	            if (httpReq.getSession().getAttribute("userID") == null) {
	                httpResp.sendRedirect(redirect);
	            }
	        }
	    }
	    chain.doFilter(httpReq, httpResp);
	}

    @Override
    public void destroy() {}

    private boolean isAjaxRequest(HttpServletRequest request) {
        boolean check = false;
        String XRequestedWith = request.getHeader("X-Requested-With");
        if (XRequestedWith != null && "XMLHttpRequest".equalsIgnoreCase(XRequestedWith)) {
            check = true;
        }
        return check;
    }
}
