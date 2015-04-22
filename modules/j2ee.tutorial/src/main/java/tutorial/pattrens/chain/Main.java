package tutorial.pattrens.chain;

/**
 * @author yuanwei
 * @version ctreateTime:2011-7-8 下午4:53:25
 */
public class Main {

	public static void main(String[] args) {
		String msg="大家好:)，<script>，敏感，被就业，网络授课没感觉，因为看不见大家伙儿";
		Request request=new Request();
		request.setRequest(msg);
		Response response=new Response();
		response.setResponse("response");
		FilterChain fc=new FilterChain();
		fc.addFilter(new HTMLFilter()).addFilter(new SesitiveFilter());

		fc.doFilter(request,response,fc);
		System.out.println(request.getRequest());
		System.out.println(response.getResponse());
	}

}
