package utils.orm.page;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class PageQuery<T> implements Serializable{
	private static final long	serialVersionUID	=2808385603673930193L;
	private int		pageSize;
	private int		pageNumber;
	private int		totalCount;
	private boolean	countTotal;
	private Map<String,Object> filters;
	private Object result;

	public PageQuery() {
		this(1,50,0);
	}
	public PageQuery(int pageNumber) {
		this(pageNumber,50,0);
	}
	public PageQuery(int pageNumber,int pageSize) {
		this(pageNumber,pageSize,0);
	}
	public PageQuery(int pageNumber,int pageSize, int totalCount) {
		this.pageSize=pageSize;
		this.pageNumber=pageNumber;
		this.totalCount=totalCount;
		if(totalCount<1)this.countTotal=true;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize=pageSize;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber=pageNumber;
	}
	public boolean isCountTotal() {
		return countTotal;
	}
	public void setCountTotal(boolean countTotal) {
		this.countTotal=countTotal;
	}

	public void setFilters(Map<String, Object> filters) {
		this.filters=filters;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount=totalCount;
	}

	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result=result;
	}
	//去掉empty name 和 null value
	public PageQuery<T> addFilter(String key,Object value){
		if(filters==null)filters=new HashMap<String, Object>();
		if(key!=null&&key.trim().length()>0&&value!=null)
			filters.put(key,value);
		return this;
	}
	public Object get(String key,Object dv){
		if(filters==null)return null;
		Object obj=null;
		if(key!=null&&key.trim().length()>0){
			obj=filters.get(key);
		}
		if(obj==null)obj=dv;
		return obj;
	}
	public PageResult<T> toPageResult(){
		return new PageResult<T>(pageSize,pageNumber,totalCount);
	}
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(countTotal).append(filters)
			.append(pageNumber).append(pageSize).append(totalCount)
				.toHashCode();
	}
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	@Override
	public String toString() {
		StringBuilder builder=new StringBuilder();
		builder.append("Query [pageSize=");
		builder.append(pageSize);
		builder.append(", pageNumber=");
		builder.append(pageNumber);
		builder.append(", totalCount=");
		builder.append(totalCount);
		builder.append(", countTotal=");
		builder.append(countTotal);
		builder.append(", filters=");
		builder.append(filters);
		builder.append("]");
		return builder.toString();
	}

}
