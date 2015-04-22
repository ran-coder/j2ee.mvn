package utils;

import java.util.Map;
import java.util.Set;

/**  
 * @author yuanwei  
 * @version ctreateTime:2012-7-9 下午4:55:22
 *   
 */
public class Conditions {
	public static <T> void add(Set<T> set,T t,boolean condition){
		if(condition)set.add(t);
	}
	public static <K,V> void add(Map<K,V> map,K k,V v,boolean condition){
		if(condition)map.put(k,v);
	}
	public static void append(StringBuilder builder,String s,boolean condition){
		if(condition)builder.append(s);
	}
}
