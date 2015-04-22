package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;
import java.util.Set;

public class CollectionUtil {
	/*public static <T> List<T> mergeAndUniqueList(List<T>... lists){
		Set<T> set=new HashSet<T>();
		for(List<T> list:lists){
			if(list!=null&& !list.isEmpty())set.addAll(list);
		}
		return new ArrayList<T>(set);
	}*/
	public static <T> List<T> mergeAndUniqueList(List<T> list1,List<T> list2){
		Set<T> set=new HashSet<T>();
		if(list1!=null&& !list1.isEmpty())set.addAll(list1);
		if(list2!=null&& !list2.isEmpty())set.addAll(list2);
		return new ArrayList<T>(set);
	}

	public static <T> boolean isNullOrEmpty(List<T> list){
		return list==null||list.isEmpty();
	}
	public static <K,V> boolean isNullOrEmpty(Map<K,V> map){
		return map==null || map.isEmpty();
	}
	public static <K,V> V getMapValue(Map<K,V> map,K key,V defaultValue){
		if(isNullOrEmpty(map) || map.get(key)==null)return defaultValue;
		return map.get(key);
	}

	/**
	 * Create a typesafe copy of a raw set.
	 * 
	 * @param rawSet
	 *            an unchecked set
	 * @param type
	 *            the desired supertype of the entries
	 * @param strict
	 *            true to throw a <code>ClassCastException</code> if the raw
	 *            set has an invalid entry, false to skip over such entries
	 *            (warnings may be logged)
	 * @return a typed set guaranteed to contain only entries assignable to the
	 *         named type (or they may be null)
	 * @throws ClassCastException
	 *             if some entry in the raw set was not well-typed, and only if
	 *             <code>strict</code> was true
	 */
	@SuppressWarnings("rawtypes")
	public static <E> Set<E> checkedSetByCopy(Set rawSet, Class<E> type, boolean strict) throws ClassCastException {
		Set<E> s=new HashSet<E>(rawSet.size() * 4 / 3 + 1);
		Iterator it=rawSet.iterator();
		while(it.hasNext()){
			Object e=it.next();
			try{
				s.add(type.cast(e));
			}catch(ClassCastException x){
				if(strict){
					throw x;
				}else{
					System.out.println("not assignable ");
				}
			}
		}
		return s;
	}

	/**
	 * Create a typesafe copy of a raw list.
	 * 
	 * @param rawList
	 *            an unchecked list
	 * @param type
	 *            the desired supertype of the entries
	 * @param strict
	 *            true to throw a <code>ClassCastException</code> if the raw
	 *            list has an invalid entry, false to skip over such entries
	 *            (warnings may be logged)
	 * @return a typed list guaranteed to contain only entries assignable to the
	 *         named type (or they may be null)
	 * @throws ClassCastException
	 *             if some entry in the raw list was not well-typed, and only if
	 *             <code>strict</code> was true
	 */
	@SuppressWarnings("rawtypes")
	public static <E> List<E> checkedListByCopy(List rawList, Class<E> type, boolean strict) throws ClassCastException {
		List<E> l=(rawList instanceof RandomAccess) ? new ArrayList<E>(rawList.size()) : new LinkedList<E>();
		Iterator it=rawList.iterator();
		while(it.hasNext()){
			Object e=it.next();
			try{
				l.add(type.cast(e));
			}catch(ClassCastException x){
				if(strict){
					throw x;
				}else{
					System.out.println("not assignable ");
				}
			}
		}
		return l;
	}

	/**
	 * Create a typesafe copy of a raw map.
	 * 
	 * @param rawMap
	 *            an unchecked map
	 * @param keyType
	 *            the desired supertype of the keys
	 * @param valueType
	 *            the desired supertype of the values
	 * @param strict
	 *            true to throw a <code>ClassCastException</code> if the raw
	 *            map has an invalid key or value, false to skip over such map
	 *            entries (warnings may be logged)
	 * @return a typed map guaranteed to contain only keys and values assignable
	 *         to the named types (or they may be null)
	 * @throws ClassCastException
	 *             if some key or value in the raw map was not well-typed, and
	 *             only if <code>strict</code> was true
	 */
	@SuppressWarnings("rawtypes")
	public static <K, V> Map<K, V> checkedMapByCopy(Map rawMap, Class<K> keyType, Class<V> valueType, boolean strict) throws ClassCastException {
		if(rawMap == null || rawMap.isEmpty()) return null;
		Map<K, V> map=new HashMap<K, V>(rawMap.size() * 4 / 3 + 1);
		Iterator it=rawMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry e=(Map.Entry)it.next();
			try{
				map.put(keyType.cast(e.getKey()),valueType.cast(e.getValue()));
			}catch(ClassCastException x){
				if(strict){
					throw x;
				}else{
					System.out.println("checkedMapByCopy " + rawMap + " not assignable");
				}
			}
		}
		return map;
	}

	public static void main(String[] args) {
		List<Integer> list1=new ArrayList<Integer>();
		list1.add(1);list1.add(2);list1.add(3);list1.add(4);
		List<Integer> list2=new ArrayList<Integer>();
		list2.add(1);list2.add(2);list2.add(3);list2.add(5);
		List<Integer> list3=new ArrayList<Integer>();
		list3.add(0);list3.add(2);list3.add(3);list3.add(4);
		List<Integer> lists=mergeAndUniqueList(list1,list2);
		System.out.println("*********************************");
		for(int i:lists){
			System.out.println(i);
		}
		System.out.println("*********************************");
		lists=mergeAndUniqueList(list1,list2);
		for(int i:lists){
			System.out.println(i);
		}
	}
}
