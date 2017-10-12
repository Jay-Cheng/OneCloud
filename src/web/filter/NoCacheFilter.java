package web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class NoCacheFilter implements Filter {
    
    public void init(FilterConfig fConfig) throws ServletException {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	    HttpServletResponse httpResp = (HttpServletResponse) response;
	    
	    httpResp.setDateHeader("Expires", -1);
	    httpResp.setHeader("Cache-Control", "no-cache");
	    httpResp.setHeader("Pragma", "no-cache");
	        
	    chain.doFilter(request, response);
	}

    @Override
    public void destroy() {}
}
