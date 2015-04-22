package j2ee.tutorial.apache.util.collections;

import java.util.Map;

import org.apache.commons.collections.map.LRUMap;

public class LRUMapTest {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Map<String,Float> cache=new LRUMap(5);

		// Populate the cache with 5 stock prices
		cache.put("MSFT",new Float(0.03));
		cache.put("TSC",new Float(0.001));
		cache.put("LU",new Float(23.30));
		cache.put("CSCO",new Float(242.20));
		cache.put("P",new Float(10.23));

		// Now use some of the entries in the cache
		cache.get("CSCO");
		cache.get("MSFT");
		cache.get("TSC");
		cache.get("LU");
		cache.get("P");
		cache.get("MSFT");

		// Add another price to the Map, this should kick out the LRU item.
		cache.put("AA",new Float(203.20));

		// CSCO was the first price requested, it is therefore the
		// least recently used.
		if(!cache.containsKey("CSCO")){
			System.out.println("As expected CSCO was discarded");
		}

	}
}
