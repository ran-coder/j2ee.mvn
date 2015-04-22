package utils.servlet;


import java.io.*;
import javax.servlet.*;
import org.apache.log4j.Logger;

public class RequestUtilFilter implements Filter {
	private Logger log = Logger.getLogger(this.getClass());
	private String encode;


	public void destroy() {
		log.debug("RequestUtilFilter destroy..");
		encode=null;
		log=null;
	}
	
	public void init(FilterConfig config) {
		if(config!=null) encode=config.getInitParameter("RequestEncoding");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		log.debug("********************* RequestUtilFilter *********************");

		chain.doFilter(request, response);
	}
	
	public void setRequestEncoding(ServletRequest request) {
		if(encode!=null)
			try {
				request.setCharacterEncoding(encode);
			} catch (Exception e) {
				System.out.println("设置Request编码失败:"+e);
			}
	}
	public void setResponseEncoding(ServletResponse response) {
		if(encode!=null)
			try {
				response.setCharacterEncoding(encode);
			} catch (Exception e) {
				System.out.println("设置Response编码失败:"+e);
			}
	}


}
