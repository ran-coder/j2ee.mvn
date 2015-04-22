package tutorial.pattrens.chain;

public class HTMLFilter implements Filter {

	public void doFilter(Request request, Response response, FilterChain chain) {
		//process the html tag <>
		request.setRequest(request.getRequest().replace('<', '[')+ "---HTMLFilter()");
		chain.doFilter(request, response, chain);
		response.setResponse(response.getResponse()+"---HTMLFilter()");
	}

}
