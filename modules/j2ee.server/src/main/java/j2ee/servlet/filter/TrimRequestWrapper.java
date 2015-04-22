package j2ee.servlet.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class TrimRequestWrapper extends HttpServletRequestWrapper {
	private final Log log = LogFactory.getLog(TrimRequestWrapper.class);

	public TrimRequestWrapper(HttpServletRequest servletRequest) {
		super(servletRequest);
	}

	public String[] getParameterValues(String parameter) {
		if(log.isDebugEnabled())log.debug("TrimRequestWrapper:"+parameter);
		String[] results=super.getParameterValues(parameter);
		if(results == null) return null;
		int count=results.length;
		String[] trimResults=new String[count];
		for(int i=0;i < count;i++){
			trimResults[i]=results[i].trim();
		}
		return trimResults;
	}
}