package utils.orm;

import java.util.List;

public interface BaseDao {

	public abstract boolean save(Object object);

	public abstract boolean saveOrUpdate(Object object);

	public abstract boolean update(Object object);

	public abstract boolean delete(Object object);

	public abstract int execute(final String updateHql, final Object[] values);

	public abstract List<?> find(final String hql, final Object[] values, final int page, final int size);

	public abstract int findCount(final String hql, final Object[] values);

}