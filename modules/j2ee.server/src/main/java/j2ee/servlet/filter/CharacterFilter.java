package j2ee.servlet.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CharacterFilter implements Filter {
	private final Log log=LogFactory.getLog(CharacterFilter.class);
	@SuppressWarnings("unused")
	private FilterConfig	filterConfig;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig=filterConfig;
	}

	public void destroy() {
		this.filterConfig=null;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,ServletException {
		if(log.isDebugEnabled())log.debug("Enter CharacterFilter.doFilter path:"+((HttpServletRequest)request).getRequestURL());
		/*HttpServletRequest req=(HttpServletRequest)request;
		if(req.getMethod().equalsIgnoreCase("get")){
			req=new GetHttpServletRequestWrapper(req,"charset");
		}*/
		chain.doFilter(new TrimRequestWrapper((HttpServletRequest)request),response);
	}
}