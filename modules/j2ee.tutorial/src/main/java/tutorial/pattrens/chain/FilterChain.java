package tutorial.pattrens.chain;

import java.util.ArrayList;
import java.util.List;

public class FilterChain implements Filter {
	private List<Filter> filters ;
	private int index = 0;
	
	public FilterChain addFilter(Filter f) {
		if(this.filters==null)filters = new ArrayList<Filter>();
		this.filters.add(f);
		return this;
	}
	
	public void doFilter(Request request, Response response, FilterChain chain) {
		if(index == filters.size()) return ;
		Filter f = filters.get(index);
		index ++;
		f.doFilter(request, response, chain);
	}
}
