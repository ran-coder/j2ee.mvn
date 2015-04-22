package j2ee.tutorial.apache.util.collections;

import java.util.Map;
import org.apache.commons.collections.map.CaseInsensitiveMap;

@SuppressWarnings("unchecked")
public class CaseInsensitiveExample {
	Map<String,Object>	states	=new CaseInsensitiveMap();

	public static void main(String[] args) {
		CaseInsensitiveExample example=new CaseInsensitiveExample();
		example.start();
	}

	private void start() {
		states.put("IL","Illinois");
		states.put("PA","Pennsylvania");
		states.put("GA","Georgia");
		states.put("AZ","Arizona");

		String stateName=(String)states.get("il");
		System.out.println("Value retrieved for 'il': " + stateName);
		stateName=(String)states.get("IL");
		System.out.println("Value retrieved for 'IL': " + stateName);
		stateName=(String)states.get("iL");
		System.out.println("Value retrieved for 'iL': " + stateName);
	}
}
