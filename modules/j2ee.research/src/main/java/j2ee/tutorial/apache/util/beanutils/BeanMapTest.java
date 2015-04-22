package j2ee.tutorial.apache.util.beanutils;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanMap;

public class BeanMapTest {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Person person=new Person();
		person.setName("Jim");
		person.setAge(Integer.valueOf(28));
		person.setOccupation("Developer");
		Map<String, Object> beanMap=new BeanMap(person);
		//bad
		/*Set<String> keys=beanMap.keySet();
		Iterator<String> keyIterator=keys.iterator();
		while(keyIterator.hasNext()){
			String propertyName=(String)keyIterator.next();
			System.out.println("Property: " + propertyName + ", Value: " + beanMap.get(propertyName));
		}*/
		for(Entry<String, Object> entry:beanMap.entrySet()){
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}

	}

}
