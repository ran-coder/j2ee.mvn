package tutorial.apache.util.collections;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.Factory;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.apache.commons.collections.map.LazyMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.commons.lang.StringUtils;

public class MutiMap {

	public static void main(String[] args) {
		//demoBidiMap();
		//demoMultiMap();
		demoLazyMap();
	}

	/****
	 * 所谓BidiMap，直译就是双向Map，可以通过key找到value，
	 * 也可以通过value找到key，这在我们日常的代码-名称匹配的时候很方便：
	 * 因为我们除了需要通过代码找到名称之外，往往也需要处理用户输入的名称，然后获取其代码。
	 * 需要注意的是BidiMap当中不光key不能重复，value也不可以。
	 */
	public static void demoBidiMap() {

		System.out.println(StringUtils.center(" demoBidiMap ",40,"="));
		BidiMap bidiMap=new DualHashBidiMap();
		bidiMap.put("BJ","Beijing");
		bidiMap.put("SH","Shanghai");
		bidiMap.put("GZ","Guangzhou");
		bidiMap.put("CD","Chengdu");
		System.out.println("Key-Value: BJ = " + bidiMap.get("BJ"));
		System.out.println("Value-Key: Chengdu = " + bidiMap.getKey("Chengdu"));
		System.out.println(StringUtils.repeat("=",40));
	}

	/****
	 * 所谓MultiMap，就是说一个key不在是简单的指向一个对象，而是一组对象，
	 * add()和remove()的时候跟普通的Map无异，只是在get()时返回一个Collection，
	 * 利用MultiMap，我们就可以很方便的往一个key上放数量不定的对象，也就实现了一对多。
	 */
	@SuppressWarnings("unchecked")
	public static void demoMultiMap() {
		System.out.println(StringUtils.center(" demoMultiMap ",40,"="));
		List<String> list=new ArrayList<String>();
		//List<String> valuelist=new ArrayList<String>();
		list.add("123");
		list.add("456");
		list.add("789");
		MultiMap multiMap=new MultiValueMap();
		multiMap.put("Sean",list);
		multiMap.put("Sean","C/C++");
		multiMap.put("Sean","OO");
		multiMap.put("Sean","Java");
		multiMap.put("Sean",".NET");
		multiMap.remove("Sean","C/C++");
		System.out.println("Sean's skill set: " + multiMap.get("Sean"));
		Set<Entry<String,List<String>>> entrySet=multiMap.entrySet();
		for(Entry<String,List<String>> entry:entrySet){
			System.out.println(entry.getKey()+"	"+java.util.Arrays.toString(entry.getValue().toArray()));
		}
		/*Iterator itet=((Collection)multiMap.get("Sean")).iterator();
		while(itet.hasNext()){
			Object obj=itet.next();
			if(obj instanceof List){
				valuelist=(List<String>)obj;
				for(Object value:valuelist){
					System.out.println("obj1:" + value);
				}

			}else if(obj instanceof String){
				System.out.println("value:" + obj.toString());
			}
		}*/
		
		System.out.println(StringUtils.repeat("=",40));
	}

	/**所谓LazyMap，意思就是这个Map中的键/值对一开始并不存在，当被调用到时才创建.
	 * 我们这样来理解：我们需要一个Map，但是由于创建成员的方法很“重”（比如数据库访问），
	 * 或者我们只有在调用get()时才知道如何创建，或者Map中出现的可能性很多很多，
	 * 我们无法在get()之前添加所有可能出现的键/值对，
	 * 我们觉得没有必要去初始化一个Map而又希望它可以在必要时自动处理数据
	 *
	 */
	public static void demoLazyMap() {
		System.out.println(StringUtils.center(" demoLazyMap ",40,"="));
		Factory factory=new Factory() {
			public Object create() {
				return new Date();
			}
		};
		@SuppressWarnings("rawtypes")
		Map lazy=LazyMap.decorate(new HashMap(),factory);
		System.out.println("map:" + lazy);//lazy为空
		System.out.println(lazy.get("123"));
		System.out.println(lazy.get("345"));
		System.out.println(StringUtils.repeat("=",40));
	}
}
