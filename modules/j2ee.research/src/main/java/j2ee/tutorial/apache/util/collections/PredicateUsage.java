package j2ee.tutorial.apache.util.collections;

import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.PredicateUtils;
import org.apache.commons.collections.functors.InstanceofPredicate;
import org.apache.commons.collections.functors.NotNullPredicate;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

public class PredicateUsage {

	public static void main(String[] args) {
		demoPredicates();
	}

	public static void demoPredicates() {
		System.out.println(StringUtils.center(" demoPredicates ", 40, "="));
		Predicate p1 = new InstanceofPredicate(String.class);
		Predicate p2 = NotNullPredicate.getInstance();
		Predicate p3 = new Predicate() {
			public boolean evaluate(Object obj) {
				String str = (String) obj;
				return StringUtils.isAlphanumeric(str) && str.length() >= 6 && str.length() <= 10;
			}
		};
		Predicate p4 = PredicateUtils.allPredicate(new Predicate[] { p1, p2, p3 });
		String input = "ABCD1234";
		Object[] raw = new Object[] { "Is '", input, "' a valid input? ",
				BooleanUtils.toStringYesNo(p4.evaluate(input)), "." };
		System.out.println(StringUtils.join(raw));
		System.out.println(StringUtils.repeat("=", 40));
	}

}