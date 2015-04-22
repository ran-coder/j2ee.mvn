package tutorial.lang.generic;

import java.util.HashMap;
import java.util.Map;

public class SingletonClassCache {
	private static Map<Class<?>, Object>	cache	=new HashMap<Class<?>, Object>();

	public static synchronized <T> void put(T t) {
		cache.put(t.getClass(),t);
	}

	public static synchronized <T> T get(Class<T> clazz) {
		return clazz.cast(cache.get(clazz));
	}

	public static void main(String[] args) {
		String s="string";
		SingletonClassCache.put(s);
		Integer i=Integer.valueOf(1);
		SingletonClassCache.put(i);

		System.out.println(s == SingletonClassCache.get(String.class));
		System.out.println(i == SingletonClassCache.get(Integer.class));
	}
}