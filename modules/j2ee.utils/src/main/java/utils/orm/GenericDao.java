package utils.orm;

import java.io.Serializable;
import java.util.Map;

public interface GenericDao<T, PK extends Serializable> {

	public void save(T entity);

	public T find(Class<T> clazz, PK id);

	public Page<T> FindAll(Map<String, Object> filter, Page<T> page);

	public void remove(T entity);

}
