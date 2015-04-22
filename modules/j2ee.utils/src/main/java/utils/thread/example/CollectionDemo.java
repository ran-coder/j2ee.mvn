package utils.thread.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollectionDemo {

	public static void main(String[] args) {
		testConcurrentList();
		testConcurrentMap();
	}

	public static void testConcurrentList() {
		long start=System.currentTimeMillis();
		long list_time=0;
		List<Integer> list=new ArrayList<Integer>();
		for(int i=0;i < 1000000;i++){
			list.add(i);
		}
		for(int i=0;i < list.size();i++){
			list.get(i);
		}
		list_time=System.currentTimeMillis() - start;

		start=System.currentTimeMillis();
		long vector_time=0;
		Vector<Integer> vec=new Vector<Integer>();
		// public synchronized void insertElementAt 同步所有方法
		for(int i=0;i < 1000000;i++){
			vec.add(i);
		}
		for(int i=0;i < vec.size();i++){
			vec.get(i);
		}
		vector_time=System.currentTimeMillis() - start;

		start=System.currentTimeMillis();
		long concurrentList_time=0;
		List<Integer> concurrentList=Collections.synchronizedList(new ArrayList<Integer>());
		// synchronized(mutex)同步需要同步的代码块
		for(int i=0;i < 1000000;i++){
			concurrentList.add(i);
		}
		for(int i=0;i < concurrentList.size();i++){
			concurrentList.get(i);
		}
		concurrentList_time=System.currentTimeMillis() - start;

		start=System.currentTimeMillis();
		long copyOnWriteArrayList_time=0;
		CopyOnWriteArrayList<Integer> copyOnWriteArrayList=new CopyOnWriteArrayList<Integer>();
		// 重入锁（ReentrantLock）是一种递归无阻塞的同步机制。
		for(int i=0;i < 1000000;i++){
			concurrentList.add(i);
		}
		for(int i=0;i < copyOnWriteArrayList.size();i++){
			copyOnWriteArrayList.get(i);
		}
		copyOnWriteArrayList_time=System.currentTimeMillis() - start;

		System.out.println("list--------------- vector--------------");
		Collections.synchronizedList(new ArrayList<Integer>());
		System.out.println("--------CopyOnWriteArrayList");
		System.out
				.println(list_time + "------------" + vector_time + "------------" + concurrentList_time + "------------" + copyOnWriteArrayList_time);
	}

	public static void testConcurrentMap() {
		long start=System.currentTimeMillis();
		long map_time=0;
		Map<String,String> map=new HashMap<String,String>();
		for(int i=0;i < 1000000;i++){
			map.put("item" + i,"value" + i);
		}
		for(int i=0;i < map.size();i++){
			map.get("item" + i);
		}
		map_time=System.currentTimeMillis() - start;

		start=System.currentTimeMillis();
		long ht_time=0;
		Hashtable<String, String> ht=new Hashtable<String, String>();
		for(int i=0;i < 1000000;i++){
			ht.put("item" + i,"value" + i);
		}
		for(int i=0;i < ht.size();i++){
			ht.get("item" + i);
		}
		ht_time=System.currentTimeMillis() - start;

		start=System.currentTimeMillis();
		long concurrentMap_time=0;
		Map<String,String> concurrentMap=Collections.synchronizedMap(new HashMap<String,String>());
		for(int i=0;i < 1000000;i++){
			concurrentMap.put("item" + i,"value" + i);
		}
		for(int i=0;i < concurrentMap.size();i++){
			concurrentMap.get("item" + i);
		}
		concurrentMap_time=System.currentTimeMillis() - start;

		start=System.currentTimeMillis();
		long copyOnWriteArrayMap_time=0;
		ConcurrentHashMap<String,String> concurrentHashMap=new ConcurrentHashMap<String,String>();
		for(int i=0;i < 1000000;i++){
			concurrentHashMap.put("item" + i,"value" + i);
		}
		for(int i=0;i < concurrentHashMap.size();i++){
			concurrentHashMap.get("item" + i);
		}
		copyOnWriteArrayMap_time=System.currentTimeMillis() - start;

		System.out.println("map--------------- hashTable--------------");
		Collections.synchronizedMap(new HashMap<String,String>());
		System.out.println("--------ConcurrentHashMap");
		System.out.println(map_time + "------------" + ht_time + "------------" + concurrentMap_time + "------------" + copyOnWriteArrayMap_time);
	}
}
