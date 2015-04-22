package utils.log;

import org.apache.log4j.Logger;

import utils.StringUtil;

public class LogUtils {
	public static void info(Logger log,String info) {
		if(log.isInfoEnabled()) {
			log.info(info);
		}
	}
	public static void info(Logger log,String info,Throwable e) {
		if(log.isInfoEnabled()) {
			log.info(info,e);
		}
	}
	public static void info(Logger log,Object[] obj) {
		if(!log.isInfoEnabled())return;
		String logs=getString(obj);
		if(!StringUtil.isEmpty(logs)) {
			log.info(logs);
		}
	}
	public static void debug(Logger log,String info) {
		if(log.isDebugEnabled()) {
			log.debug(info);
		}
	}
	public static void debug(Logger log,Object[] obj) {
		if(!log.isDebugEnabled())return;
		
		String logs=getString(obj);
		if(!StringUtil.isEmpty(logs)) {
			log.debug(logs);
		}
	}
	public static String getString(Object[] obj) {
		if(obj!=null && obj.length>0) {
			StringBuffer rs=new StringBuffer("");
			for (int i = 0; i < obj.length; i++) {
				Object object = obj[i];
				if(object!=null)
					rs.append(object.toString());
			}
			return (rs.toString());
		}
		return "";
	}
}
