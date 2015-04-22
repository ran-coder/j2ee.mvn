package j2ee.tutorial.pattrens.chain;

public class SesitiveFilter implements Filter {

	public void doFilter(Request request, Response response, FilterChain chain) {
		request.setRequest(request.getRequest().replace('<', '[')+ "---SesitiveFilter()");
		chain.doFilter(request, response, chain);
		response.setResponse(response.getResponse()+"---SesitiveFilter()");
	
	}
	
	

}
