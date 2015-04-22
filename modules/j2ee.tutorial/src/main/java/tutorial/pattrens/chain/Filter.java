package tutorial.pattrens.chain;
/**  
 * @author yuanwei  
 * @version ctreateTime:2011-7-8 下午4:50:18
 *   
 */
public interface Filter {
	public void doFilter(Request request, Response response, FilterChain chain);
}
