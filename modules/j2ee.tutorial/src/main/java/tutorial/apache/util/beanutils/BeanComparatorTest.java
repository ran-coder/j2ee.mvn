package tutorial.apache.util.beanutils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;

public class BeanComparatorTest {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Country country1=new Country();
		country1.setName("India");
		Country country2=new Country();
		country2.setName("Pakistan");
		Country country3=new Country();
		country3.setName("Afghanistan");
		// Create a List of Country objects
		Country[] countryArray=new Country[]{ country1, country2, country3 };
		List<Country> countryList=Arrays.asList(countryArray);
		// Sort countries by name
		Comparator<Country> nameCompare=new BeanComparator("name");
		Collections.sort(countryList,nameCompare);
		System.out.println("Sorted Countries:");
		Iterator<Country> countryIterator=countryList.iterator();
		while(countryIterator.hasNext()){
			Country country=countryIterator.next();
			System.out.println("\tCountry: " + country.getName());
		}

	}
}
