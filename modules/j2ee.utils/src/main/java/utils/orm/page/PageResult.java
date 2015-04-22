package utils.orm.page;

import java.util.List;

/**
 * 与具体ORM实现无关的分页参数及查询结果封装. 兼容com.fltrp.util.Page
 */
public class PageResult<T> {
	protected int		pageSize;
	protected int		pageNumber;
	protected int		totalCount;
	protected List<T> result;

	public PageResult(){}
	public PageResult(int pageSize, int pageNumber, int totalCount) {
		this.pageSize=pageSize;
		this.pageNumber=pageNumber;
		this.totalCount=totalCount;
	}
	public PageResult(int pageSize, int pageNumber, int totalCount, List<T> result) {
		this.pageSize=pageSize;
		this.pageNumber=pageNumber;
		this.totalCount=totalCount;
		this.result=result;
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
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount=totalCount;
	}
	public List<T> getResult() {
		return result;
	}
	public List<T> getThisPageElements() {
		return result;
	}
	public void setResult(List<T> result) {
		this.result=result;
	}
	public int getStart(){
		int start=(pageNumber-1)*pageSize-1;
		if(start<0)start=0;
		return start;
	}

	@Override
	public int hashCode() {
		final int prime=31;
		int result=1;
		result=prime * result + pageNumber;
		result=prime * result + pageSize;
		result=prime * result + ((this.result == null) ? 0 : this.result.hashCode());
		result=prime * result + (int)(totalCount ^ (totalCount >>> 32));

		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		PageResult<?> other=(PageResult<?>)obj;
		if(pageNumber != other.pageNumber) return false;
		if(pageSize != other.pageSize) return false;
		if(result == null){
			if(other.result != null) return false;
		}else if(!result.equals(other.result)) return false;
		if(totalCount != other.totalCount) return false;
		return true;
	}
	@Override
	public String toString() {
		StringBuilder builder=new StringBuilder();
		builder.append("PurePage [pageNumber=");
		builder.append(pageNumber);
		builder.append(", pageSize=");
		builder.append(pageSize);
		builder.append(", result=");
		builder.append(result);
		builder.append(", totalCount=");
		builder.append(totalCount);
		builder.append("]");
		return builder.toString();
	}

	/** 更新 */
	public PageResult<T> update(){
		if(result!=null&&result.size()>0){
			if(totalCount<1)
				totalCount=result.size();
		}else{
			result=null;
		}
		return this;
	}


	public static <T> int totalCount(PageResult<T> page){
		return page==null?0:page.getTotalCount();
	}
	public static <T> List<T> result(PageResult<T> page){
		return page==null?null:page.getResult();
	}
}

