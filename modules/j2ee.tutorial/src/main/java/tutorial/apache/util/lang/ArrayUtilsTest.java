package tutorial.apache.util.lang;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

public class ArrayUtilsTest {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws UnsupportedEncodingException {
		int[] intArray = new int[] { 2, 3, 4, 5, 6 };
		int[][] multiDimension = new int[][] { { 1, 2, 3 }, { 2, 3 }, {5, 6, 7} };
		System.out.println( "intArray: " + ArrayUtils.toString( intArray ) );
		System.out.println( "multiDimension: " + ArrayUtils.toString( multiDimension ) );
		System.out.println(ArrayUtils.toString("Solution".getBytes("utf-8")));

		List<String> list = new ArrayList<String>( );
		list.add( "Foo" );
		list.add( "Blah" );
		System.out.println( ArrayUtils.toString( list.toArray( ) ) );

		long[] array = { 1, 3, 2, 3, 5, 6 };
		long[] reversed = ArrayUtils.clone( array );
		ArrayUtils.reverse( reversed );
		System.out.println( "Original: " + ArrayUtils.toString( array ) );
		System.out.println( "Reversed: " + ArrayUtils.toString( reversed ) );

		Long[] array1 = new Long[] { Long.valueOf(3), Long.valueOf(56), Long.valueOf(233) };
		Long[] reversed1 = (Long[])ArrayUtils.clone( array1 );
		ArrayUtils.reverse( reversed1 );
		System.out.println( "Original: " + ArrayUtils.toString( array1 ) );
		System.out.println( "Reversed: " + ArrayUtils.toString( reversed1 ) );

		String[] stringArray = { "Red", "Orange", "Blue", "Brown", "Red" };
		boolean containsBlue = ArrayUtils.contains( stringArray, "Blue" );
		int indexOfRed = ArrayUtils.indexOf( stringArray, "Red");
		int lastIndexOfRed = ArrayUtils.lastIndexOf( stringArray, "Red" );
		System.out.println( "Array contains 'Blue'? " + containsBlue );
		System.out.println( "Index of 'Red'? " + indexOfRed );
		System.out.println( "Last Index of 'Red'? " + lastIndexOfRed );

		Object[] weightArray =
		    new Object[][] { {"H" , new Double( 1.007)},
		                     {"He", new Double( 4.002)},
		                     {"Li", new Double( 6.941)},
		                     {"Be", new Double( 9.012)},
		                     {"B",  new Double(10.811)},
		                     {"C",  new Double(12.010)},
		                     {"N",  new Double(14.007)},
		                     {"O",  new Double(15.999)},
		                     {"F",  new Double(18.998)},
		                     {"Ne", new Double(20.180)} };
		// Create a Map mapping colors.
		Map<String,Double> weights = ArrayUtils.toMap( weightArray );
		System.out.println(weights.get( "H" ));


	}
}
