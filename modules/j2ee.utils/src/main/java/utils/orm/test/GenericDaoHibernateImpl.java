package utils.orm.test;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


public class GenericDaoHibernateImpl<T> extends HibernateDaoSupport implements GenericDao<T> {
	private static final long	serialVersionUID	=-7360034685058524770L;

	/**
	 * 添加
	 * 
	 * @param Object
	 */
	public void addEntity(T o) {
		getHibernateTemplate().save(o);
	}

	/**
	 * 删除
	 * 
	 * @param Object
	 */
	public void delEntity(T o) {
		getHibernateTemplate().delete(o);
	}

	/**
	 * 根据主键查询单个对象
	 * 
	 * @param Class
	 * @param primary
	 *            key
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	public Object getEntity(@SuppressWarnings("rawtypes") Class clz, Serializable pk) {
		return (T) getHibernateTemplate().get(clz, pk);
	}
	
	public Object getEntity(String hql,Object[] objs) {
		Object obj = getHibernateTemplate().find(hql, objs).get(0);
		return obj;
	}

	/**
	 * 根据Hql查询
	 * 
	 * @param String
	 *            hql
	 * @param Object
	 *            [] objs
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List<T> getListbyHql(String hql, Object[] objs) {
		try {
			List<T> list;
			if (objs != null) {
				list = getHibernateTemplate().find(hql, objs);
			} else {
				list = getHibernateTemplate().find(hql);
			}
			return list;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (getSession().isOpen()) {
				getSession().close();
			}
		}
	}	

	@SuppressWarnings("unchecked")
	public List<T> getListbyHql(String hql, Integer estid) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		try {
			Query query = session.createQuery(hql);
			query.setParameter(0, estid, StandardBasicTypes.INTEGER);
			query.setFirstResult(0);
			query.setMaxResults(10);
			List<T> list = query.list();
			return list;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> getListbyHql(String hql, String status,int page) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		try {
			Query query = session.createQuery(hql);
			query.setParameter(0, status, StandardBasicTypes.STRING);
			query.setFirstResult((page - 1) * 10);
			query.setMaxResults(10);
			List<T> list = query.list();
			return list;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
	}
	@SuppressWarnings("unchecked")
	public List<T> getListbyHql(String hql, Object[] objs,int page,int pg) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		try {
			Query query = session.createQuery(hql);
			if (null!=objs) {
				for (int i = 0,n=objs.length; i < n; i++) {
					query.setParameter(i, objs[i]);
				}
			}
			query.setFirstResult((page - 1) * pg);
			query.setMaxResults(pg);
			List<T> list = query.list();
			return list;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
	}

	/**
	 * 根据HQL查单个String
	 */
	public String getStrbyhql(String hql, Object[] objs) {
		String str = (String) getHibernateTemplate().find(hql, objs).get(0);
		if (getSession().isOpen()) {
			getSession().close();
		}
		return str;
	}

	@SuppressWarnings("unchecked")
	public List<T> geStrList(String hql) {
		List<T> list = getHibernateTemplate().find(hql);
		if (getSession().isOpen()) {
			getSession().close();
		}
		return list;
	}

	public Object getObjbystr(String hql, String str) {
		Object obj = getHibernateTemplate().find(hql, str).get(0);
		getSession().close();
		return obj;
	}

	/**
	 * 根据HQL更新
	 */
	public void updateByhql(String hql, Object[] objs) {
		try {
			getHibernateTemplate().bulkUpdate(hql, objs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (getSession().isOpen()) {
//				getHibernateTemplate().getSessionFactory().
				getSession().close();
			}
		}
	}

	/**
	 * 获取分页页数
	 * 
	 * @param String
	 *            hql
	 * @return int page
	 */
	public Integer getPages(String hql) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		Integer count = (Integer) session.createQuery(hql).uniqueResult();
		Integer pg = 35;
		Integer p = count % pg;
		Integer page = count / pg;
		if (p > 0) {
			page++;
		}
		session.close();
		return page;
	}

	public Integer getPages(String hql, Integer deptid) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		Query query = session.createQuery(hql);
		query.setParameter(0, deptid, StandardBasicTypes.INTEGER);
		Integer count = (Integer) query.uniqueResult();
		Integer pg = 35;
		Integer p = count % pg;
		Integer page = count / pg;
		if (p > 0) {
			page++;
		}
		session.close();
		return page;
	}

	public Integer getPages(String hql, String str) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		Query query = session.createQuery(hql);
		query.setParameter(0, str, StandardBasicTypes.STRING);
		Integer count = (Integer) query.uniqueResult();
		Integer pg = 35;
		Integer p = count % pg;
		Integer page = count / pg;
		if (p > 0) {
			page++;
		}
		session.close();
		return page;
	}

	public Integer getAPages(Object[] objs, String hql) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		Query query = session.createQuery(hql);
		Integer month = (Integer) objs[0];
		Integer year = (Integer) objs[1];
		if (month != -1) {
			query.setParameter(0, month, StandardBasicTypes.INTEGER);
			query.setParameter(1, year, StandardBasicTypes.INTEGER);
		}
		Integer count = (Integer) query.uniqueResult();
		Integer pg = 35;
		Integer p = count % pg;
		Integer page = count / pg;
		if (p > 0) {
			page++;
		}
		session.close();
		return page;
	}
	
	public Integer getNpages(String hql) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		Integer count = (Integer) session.createQuery(hql).uniqueResult();
		Integer pg = 10;
		Integer p = count % pg;
		Integer page = count / pg;
		if (p > 0) {
			page++;
		}
		session.close();
		return page;
	}
	
	public Integer gp(String hql,Object[] objs,int pg) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Query query = session.createQuery(hql);
		if (null!=objs) {
			for (int i = 0,n=objs.length; i < n; i++) {
				query.setParameter(i, objs[i]);
			}
		}
		Integer count = (Integer) query.uniqueResult();
		Integer p = count % pg;
		Integer page = count/pg;
		if (p>0) {
			page++;
		}
		session.close();
		return page;
	}

	/**
	 * 分页查询
	 * 
	 * @param String
	 *            hql
	 * @param Object
	 *            [] objs
	 * @param int page
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List<T> getListbyPage(String hql, Object[] objs, int page) {
		List<T> list = null;
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		try {
			Query query = session.createQuery(hql);
			if (null != objs) {
				for (int i = 0, n = objs.length; i < n; i++) {
					query.setParameter(i, objs[i]);
				}
			}
			query.setFirstResult((page - 1) * 35);
			query.setMaxResults(35);
			list = query.list();
		} catch (RuntimeException e) {
			list = null;
			e.printStackTrace();
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
		return list;
	}

	/**
	 * 模糊查询页数
	 * 
	 * @param hql
	 * @param objs
	 *            []
	 * @return page
	 */
	public int getBlurredPage(String hql, Object[] objs) {
		int page;
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		try {
			Query query = session.createQuery(hql);
			for (int i = 0, n = objs.length; i < n; i++) {
				query.setParameter(i, objs[i]);
			}
			Integer count = (Integer) query.uniqueResult();
			int pg = 35;
			int p = count % pg;
			page = count / pg;
			if (p > 0) {
				page++;
			}
			return page;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
	}

	/**
	 * 模糊查询结果分页
	 * 
	 * @param hql
	 * @param objs
	 *            []
	 * @param page
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List<T> getBlurredList(String hql, Object[] objs, int page) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		try {
			Query query = session.createQuery(hql);
			if (null != objs) {
				for (int i = 0, n = objs.length; i < n; i++) {
					query.setParameter(i, objs[i]);
				}
			}
			query.setFirstResult((page - 1) * 35);
			query.setMaxResults(35);
			List<T> list = query.list();
			return list;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
	}

	/**
	 * 模糊查询
	 * 
	 * @param hql
	 * @param objs
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> getGuessList(String hql, Object[] objs) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		try {
			Query query = session.createQuery(hql);
			if (null != objs) {
				for (int i = 0, n = objs.length; i < n; i++) {
					query.setParameter(i, objs[i]);
				}
			}
			List<T> list = query.list();
			return list;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
	}

	/**
	 * 非空验证
	 * 
	 * @param objs
	 *            []
	 * @param hql
	 * @return boolean
	 */
	@SuppressWarnings("unchecked")
	public boolean cbObjs(Object[] objs, String hql) {
		boolean flag = false;
		try {
			List<T> list = getHibernateTemplate().find(hql, objs);
			flag = list.size() > 0 ? true : false;
		} catch (RuntimeException e) {
			flag = false;
			e.printStackTrace();
		} finally {
			if (getSession().isOpen()) {
				getSession().close();
			}
		}
		return flag;
	}

	/**
	 * 更新单个对象
	 * 
	 * @param Object
	 */
	public void updateEntity(T o) {
		getHibernateTemplate().saveOrUpdate(o);
		if (getSession().isOpen()) {
			getSession().close();
		}
	}

	public Double getDouble(String hql, Object[] objs) {
		Double c = (Double) getHibernateTemplate().find(hql, objs).get(0);
		if (getSession().isOpen()) {
			getSession().close();
		}
		return c;
	}


	public List<T> getListbyHql(String hql, int page) {
		// TODO Auto-generated method stub
		return null;
	}
}

