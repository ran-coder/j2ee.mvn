package utils.db;

import java.util.ArrayList;
import java.util.List;

public class PageResultSet<T> {

	/** 当前页起始记录号 */
	private Integer	firstItemNum;
	/** 每页显示的记录数 */
	private Integer	pageSize;
	/** 总记录条数 */
	private Integer	totalRecordsCount;
	/** 分页后结果集 */
	private List<T>	paginatedList	=new ArrayList<T>();

	public PageResultSet() {}

	public PageResultSet(int pageSize, int firstItemNum, int totalRecordsCount, List<T> result) {
		this.firstItemNum=firstItemNum;
		this.pageSize=pageSize;
		this.totalRecordsCount=totalRecordsCount;
		this.paginatedList=result;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize=pageSize;
	}

	public List<T> getPaginatedList() {
		return paginatedList;
	}

	public void setPaginatedList(List<T> paginatedList) {
		this.paginatedList=paginatedList;
	}

	public Integer getFirstItemNum() {
		return firstItemNum;
	}

	public void setFirstItemNum(Integer firstItemNum) {
		this.firstItemNum=firstItemNum;
	}

	public Integer getTotalRecordsCount() {
		return totalRecordsCount;
	}

	public void setTotalRecordsCount(Integer totalRecordsCount) {
		this.totalRecordsCount=totalRecordsCount;
	}

}
