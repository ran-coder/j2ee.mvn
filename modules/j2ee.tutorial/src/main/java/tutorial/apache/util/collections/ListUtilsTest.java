package tutorial.apache.util.collections;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.ListUtils;

import org.apache.commons.collections.Factory;

public class ListUtilsTest {
	@SuppressWarnings("unchecked")
	public static void lazyList() {
		Factory factory=new Factory() {
			public Object create() {
				System.out.println(1);
				return new Date();
			}
		};
		List<Object> lazy=ListUtils.lazyList(new ArrayList<Object>(),factory);
		System.out.println(lazy.get(3));

	}

	public static void main(String[] args) {
		lazyList();
	}

}
