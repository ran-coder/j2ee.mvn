package utils.map;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class ProxyMap<K,V> {
	protected Map<K,V>	map;

	public ProxyMap(Map<K,V> map) {
		super();
		this.map=map;
	}

	public Map<K,V> getMap() {
		return map;
	}

	public void clear() {
		map.clear();
	}

	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	public Set<Map.Entry<K, V>> entrySet() {
		return map.entrySet();
	}

	public boolean equals(Object o) {
		return map.equals(o);
	}

	public Object get(Object key) {
		return map.get(key);
	}

	public int hashCode() {
		return map.hashCode();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public Set<K> keySet() {
		return map.keySet();
	}

	public Object put(K arg0, V arg1) {
		return map.put(arg0,arg1);
	}

	public void putAll(Map<K,V> arg0) {
		map.putAll(arg0);
	}

	public Object remove(Object key) {
		return map.remove(key);
	}

	public int size() {
		return map.size();
	}

	public Collection<V> values() {
		return map.values();
	}

}
