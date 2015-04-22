package utils.orm;

import java.sql.SQLException;
import java.util.List;


import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import utils.StringUtil;

public class CommonDaoHibernateImpl14 extends HibernateDaoSupport implements BaseDao{
	private static Logger log=Logger.getLogger(CommonDaoHibernateImpl14.class);
	protected void initDao() {	}
	
	/* (non-Javadoc)
	 * @see webutil.dao.GenericDao#save(java.lang.Object)
	 */
	public boolean save(Object object){
		try {
			getHibernateTemplate().save(object);
			return true;
		} catch (RuntimeException re) {
			log.error("save failed:", re);
			throw re;
		}
		//return false;
	}
	
	/* (non-Javadoc)
	 * @see webutil.dao.GenericDao#saveOrUpdate(java.lang.Object)
	 */
	public boolean saveOrUpdate(Object object){
		try {
			getHibernateTemplate().saveOrUpdate(object);
			return true;
		} catch (RuntimeException re) {
			log.error("saveOrUpdate failed:", re);
			throw re;
		}
		//return false;
	}
	/* (non-Javadoc)
	 * @see webutil.dao.GenericDao#update(java.lang.Object)
	 */
	public boolean update(Object object){
		try {
			getHibernateTemplate().update(object);
			return true;
		} catch (RuntimeException re) {
			log.error("save failed:", re);
			throw re;
		}
		//return false;
	}
	/* (non-Javadoc)
	 * @see webutil.dao.GenericDao#delete(java.lang.Object)
	 */
	public boolean delete(Object object) {
         try {
            getHibernateTemplate().delete(object);
            return true;
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
        //return false;
    }
	/*public List findAll(Object object) {
		try {
			String queryString = "from "+object.getClass().getSimpleName();
		 	return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
		}
		return null;
	}*/
	
    /* (non-Javadoc)
	 * @see webutil.dao.GenericDao#execute(java.lang.String, java.lang.Object[])
	 */
	public int execute(final String updateHql, final Object[] values) {
        try {
        	Object obj=getHibernateTemplate().execute(new HibernateCallback<Object>(){
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					if(session==null)throw new SQLException("Connection Session is null!");
					Query query = session.createQuery(updateHql);
					if(values!=null) {
						for(int i=0; i<values.length; i++) {
							if(!StringUtil.isEmpty(values[i].toString()))
								query.setParameter(i, values[i]);
			            }
		            }
					return Integer.valueOf(query.executeUpdate());
				} 
        	});
        	return ((Integer)obj).intValue();
        } catch (RuntimeException re) {
            log.error("executeUpdate failed", re);
            throw re;
        }
        //return 0;
    }
	
    /* (non-Javadoc)
	 * @see webutil.dao.GenericDao#find(java.lang.String, java.lang.Object[], int, int)
	 */


	public List<?> find(final String hql, final Object[] values,final int page, final int size) {
    	try {
    		List<?> list= (List<?>)getHibernateTemplate().execute(new HibernateCallback<Object>(){
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					if(session==null)throw new SQLException("Connection Session is null!");
					Query query = session.createQuery(hql);
					if(values!=null) {
						for(int i=0; i<values.length; i++) {
							if(!StringUtil.isEmpty(values[i]) && !StringUtil.isEmpty(values[i].toString()))
								query.setParameter(i, values[i]);
			            }
		            }
					if(page>0) query.setFirstResult((page-1)*size);
					
					if(size>0) query.setMaxResults(size);
					return query.list();
				} 
        	});
    		if(list==null || list.size()<1)return null;
    		return list;
        } catch (RuntimeException re) {
            log.error("GenericDAO-find failed", re);
            throw re;
        }
        //return null;
    }
    
    /* (non-Javadoc)
	 * @see webutil.dao.GenericDao#findCount(java.lang.String, java.lang.Object[])
	 */
	public int findCount(final String hql, final Object[] values) {
        try {
        	Object obj=getHibernateTemplate().execute(new HibernateCallback<Object>(){
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
        			if(session==null)throw new SQLException("Connection Session is null!");
        			Query query = session.createQuery(hql);
        			if(values!=null) {
						for(int i=0; i<values.length; i++) {
							if(!StringUtil.isEmpty(values[i].toString()))
								query.setParameter(i, values[i]);
			            }
					}
        			@SuppressWarnings("rawtypes")
					List list=query.list();
        			if(list==null || list.size()<1) {
        				return "0";
        			}else {
        				return list.get(0);
        			}
        		} 
        	});
        	return StringUtil.toInt(obj.toString());
        } catch (RuntimeException re) {
            log.error("GenericDAO-findCount get failed", re);
            throw re;
        }
        //return 0;
    }
}
