package tutorial.apache.util.collections;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.EqualPredicate;
import org.apache.commons.lang.ArrayUtils;

public class CollectionUtilsTest {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		String[] array=new String[]{ "A", "B", "C", "C", "B", "B" };
		List<String> stringList=Arrays.asList(array);
		Predicate equalPredicate=new EqualPredicate("C");
		int numberCs=CollectionUtils.countMatches(stringList,equalPredicate);
		int numberBs=CollectionUtils.cardinality("B",stringList);
		System.out.println("List: " + ArrayUtils.toString(array));
		System.out.println("Number of Cs: " + numberCs);
		System.out.println("Number of Bs: " + numberBs);

		array = new String[] { "Red", "Blue", "Blue", "Yellow", "Red","Black" };
		stringList = Arrays.asList( array );
		Map<String,?> cardinalityMap = CollectionUtils.getCardinalityMap( stringList );
		System.out.println( "List: " + ArrayUtils.toString( array ) );
		MapUtils.debugPrint(System.out,"Cardinality Map:",cardinalityMap);

	}
}
