package j2ee.tutorial.lang.generic;

import java.util.Collection;
import java.util.Iterator;

public class GenericFactory {

	private static NumberWrapper<Number>	numWrapper	=new NumberWrapper<Number>() {
															public double square(Number num) {
																return num.doubleValue() * num.doubleValue();
															}
														};

	@SuppressWarnings("unchecked")
	public static <T extends Number> NumberWrapper<T> getWrapperInstance() {
		return (NumberWrapper<T>)numWrapper;
	}

	public static <T extends Object & Comparable<? super T>> T max(Collection<? extends T> coll) {
		Iterator<? extends T> i=coll.iterator();
		T candidate=i.next();

		while(i.hasNext()){
			T next=i.next();
			if(next.compareTo(candidate) > 0) candidate=next;
		}
		return candidate;
	}

	public static void main(String[] args) {
		NumberWrapper<Integer> integerWrapper=GenericFactory.getWrapperInstance();
		System.out.println(integerWrapper.square(2));

		NumberWrapper<Double> doubleWrapper=GenericFactory.getWrapperInstance();
		System.out.println(doubleWrapper.square(0.05));
	}

}
