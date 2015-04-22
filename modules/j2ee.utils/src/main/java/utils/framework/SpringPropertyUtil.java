package utils.framework;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import utils.Numbers;


public class SpringPropertyUtil extends PropertyPlaceholderConfigurer {
	private static Logger log=LoggerFactory.getLogger(SpringPropertyUtil.class);
	private static Map<String, String>	propertiesMap;

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) throws BeansException {
		super.processProperties(beanFactory,props);
		propertiesMap=new HashMap<String, String>();
		for(Object key:props.keySet()){
			String keyStr=key.toString();
			propertiesMap.put(keyStr, props.getProperty(keyStr) );
			//propertiesMap.put(keyStr, parseStringValue(props.getProperty(keyStr),props,null)  );
		}
		if(log.isInfoEnabled()){
			log.info("processProperties,size="+(propertiesMap==null?0:propertiesMap.size()));
		}
		propertiesToString(props);
	}

	public static String getProperty(String key,String defaultValue) {
		String val=propertiesMap.get(key);
		return val==null?defaultValue:val;
	}
	public static Long getLong(String key,Long defaultValue) {
		return Numbers.parseLong(getProperty("recommendation.resellerid.headquarters",null),defaultValue);
	}
	public static Integer getInt(String key,Integer defaultValue) {
		return Numbers.parseInt(getProperty("recommendation.resellerid.headquarters",null),defaultValue);
	}
	public static void propertiesToString(Properties props){
		if(props!=null){
			props.list(System.out);
		}
	}
}
