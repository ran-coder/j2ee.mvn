package tutorial.apache.util.collections;

import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.EqualPredicate;
import org.apache.commons.collections.functors.InstanceofPredicate;
import org.apache.commons.collections.functors.NotNullPredicate;
import org.apache.commons.collections.functors.NullPredicate;
import org.apache.commons.collections.functors.UniquePredicate;

public class PredicateTest {
	public static void main(String[] args) {
		String name = "Tim";
		Predicate nameJohn = new EqualPredicate( "John" );
		Predicate nameTim = new EqualPredicate( "Tim" );
		Predicate instanceString = new InstanceofPredicate( String.class );
		Predicate instanceDouble = new InstanceofPredicate( Double.class );
		// Testing all predicates for "Tim"
		System.out.println( "Is Name John?: " + nameJohn.evaluate( name ) );
		System.out.println( "Is Name Tim?: " + nameTim.evaluate( name ) );
		System.out.println( "Is this a String?: " + instanceString.evaluate( name ) );
		System.out.println( "Is this a Double?: " + instanceDouble.evaluate( name ) );

		String nullString = null;
		Double testDouble = new Double(3.4);
		Predicate isString = new InstanceofPredicate( String.class );
		Predicate isLong = new InstanceofPredicate( Long.class );
		Predicate isNumber = new InstanceofPredicate( Number.class );
		Predicate isNotNull = NotNullPredicate.INSTANCE;
		Predicate isNull = NullPredicate.INSTANCE;
		Predicate unique = new UniquePredicate( );
		System.out.println("'nullString' not null?: " + isNotNull.evaluate(nullString));
		System.out.println("'nullString' null?: " + isNull.evaluate(nullString));
		System.out.println("'testDouble' a String?: " + isString.evaluate(testDouble));
		System.out.println("'testDouble' a Long?: " + isLong.evaluate(testDouble));
		System.out.println("'testDouble' a Number?: " + isNumber.evaluate(testDouble));
		System.out.println("'A' Unique?: " + unique.evaluate("A"));
		System.out.println("'C' Unique?: " + unique.evaluate("C"));
		System.out.println("'A' Unique?: " + unique.evaluate("A"));
		System.out.println("'B' Unique?: " + unique.evaluate("B"));

	}
}
