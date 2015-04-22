package utils.orm.hibernate;

import java.io.Serializable;
import java.util.Map;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import utils.orm.GenericDao;
import utils.orm.Page;


/**
 * @author yuanwei
 * @version ctreateTime:2011-8-8 下午02:23:46
 */
public abstract class GenericDaoHibernateImpl<T, PK extends Serializable> extends HibernateDaoSupport implements GenericDao<T, PK> {
	public void remove(T entity) {
		getHibernateTemplate().delete(entity);
	}

	public T get(Class<T> clazz,PK id) {
		return (T)getHibernateTemplate().get(clazz,id);
	}
	public abstract Page<T> getAll(Map<String,Object> filter,Page<T> page);

	public void save(T entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}

}
