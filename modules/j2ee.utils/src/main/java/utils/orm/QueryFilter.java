package utils.orm;

import java.util.HashMap;
import java.util.Map;

/**  
 * @author yuanwei  
 * @version ctreateTime:2011-9-21 上午10:43:46
 *   
 */
public class QueryFilter<T> {
	private Map<String, Object> filter;
	private Page<T> page;
	public Map<String, Object> getFilter() {
		return filter;
	}
	public void setFilter(Map<String, Object> filter) {
		this.filter=filter;
	}
	public Page<T> getPage() {
		return page;
	}
	public void setPage(Page<T> page) {
		this.page=page;
	}

	public static <T> QueryFilter<T> newInstance(){
		return new QueryFilter<T>();
	}
	public QueryFilter<T> addQuery(String key, Object value){
		if(filter==null)filter=new HashMap<String, Object>();
		filter.put(key,value);
		return this;
	}
	public QueryFilter<T> addPage(int pageSize, int pageNumber, int totalCount){
		if(page==null)page=new Page<T>();
		page.setPageSize(pageSize);
		page.setPageNumber(pageNumber);
		page.setTotalCount(totalCount);
		return this;
	}

	@Override
	public int hashCode() {
		final int prime=31;
		int result=1;
		result=prime * result + ((filter == null) ? 0 : filter.hashCode());
		result=prime * result + ((page == null) ? 0 : page.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		QueryFilter<?> other=(QueryFilter<?>)obj;
		if(filter == null){
			if(other.filter != null) return false;
		}else if(!filter.equals(other.filter)) return false;
		if(page == null){
			if(other.page != null) return false;
		}else if(!page.equals(other.page)) return false;
		return true;
	}
	@Override
	public String toString() {
		StringBuilder builder=new StringBuilder();
		builder.append("\"QueryFilter\":{\"filter\":\"");
		builder.append(filter);
		builder.append("\", page\":\"");
		builder.append(page);
		builder.append("}");
		return builder.toString();
	}
	
}
