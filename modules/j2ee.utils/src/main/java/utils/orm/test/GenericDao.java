package utils.orm.test;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T> extends Serializable {
	void addEntity(T o);

	void delEntity(T o);

	void updateEntity(T o);

	T getEntity(Class<T> clz, Serializable pk);
	
	Object getEntity(String hql,Object[] objs);

	Integer getPages(String hql);
	
	Integer getPages(String hql,Integer deptid);
	
	int getBlurredPage(String hql, Object[] objs);
	
	List<T> getListbyHql(String hql,Object[] objs);
	
	List<T> getListbyHql(String hql,int page);
	
	List<T> getListbyPage(String hql, Object[] objs, int page);
	
	List<T> getBlurredList(String hql, Object[] objs, int page);
	
	List<T> getGuessList(String hql,Object[] objs);
	
	boolean cbObjs(Object[] objs, String hql);
	
	Integer getPages(String hql,String str);
	
	Integer getNpages(String hql);
	
	Integer gp(String hql,Object[] objs,int pg);
	
	String getStrbyhql(String hql, Object[] objs);
	
	void updateByhql(String hql, Object[] objs);
	
	public Integer getAPages(Object[] objs, String hql);
	
	Object getObjbystr(String hql,String str);
	
	Double getDouble(String hql,Object[] objs);
	
	List<T> getListbyHql(String hql,Integer estid);
	
	List<T> getListbyHql(String hql,String status,int page);
	
	List<T> getListbyHql(String hql, Object[] objs,int page,int pg);
	
}

