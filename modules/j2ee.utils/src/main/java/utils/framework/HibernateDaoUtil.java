package utils.framework;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;

public class HibernateDaoUtil {
	public static int getTotalRecordsCount(final Criteria criteria){
		Integer rowCount=null;
		if(criteria!=null){
			rowCount=(Integer) criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).setProjection(Projections.rowCount()).uniqueResult();
			criteria.setProjection(null);
		}
		if(rowCount==null||rowCount<1)rowCount=0;
		return rowCount;
	}
	public static List<?> getRecords(final Criteria criteria,final Integer pageNumber,final Integer pageSize){
		if(pageSize!=null && pageSize>0){
			criteria.setMaxResults(pageSize);
			if(pageNumber!=null  && pageNumber>0)	criteria.setFirstResult((pageNumber-1)*pageSize);
			else criteria.setFirstResult(0);
		}
		return criteria.list();
	}
}
