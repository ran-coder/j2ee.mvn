package utils.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IndexMap<K,V> extends ProxyMap<K,V> {

	public IndexMap(Map<K,V> map) {
		super(map);
	}

	private List<K>	list	=new ArrayList<K>();

	public Object put(K key, V value) {
		if(!containsKey(key)){
			list.add(key);
		}
		return super.put(key,value);
	}

	public Object get(int idx) {
		return super.get(getKey(idx));
	}

	public int getIndex(Object key) {
		return list.indexOf(key);
	}

	public Object getKey(int idx) {
		if(idx >= list.size()) return null;
		return list.get(idx);
	}

	public void remove(int idx) {
		Object key=getKey(idx);
		removeFromList(getIndex(key));
		super.remove(key);
	}

	public Object remove(Object key) {
		removeFromList(getIndex(key));
		return super.remove(key);
	}

	public void clear() {
		this.list=new ArrayList<K>();
		super.clear();
	}

	private void removeFromList(int idx) {
		if(idx < list.size() && idx >= 0){
			list.remove(idx);
		}
	}
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return super.equals(o);
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
}