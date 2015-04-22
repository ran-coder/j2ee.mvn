package j2ee.tutorial.google.guice;

import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import static org.junit.Assert.*;

/*
 * java中的泛型信息在运行时会被擦除，但在两种情况下会保留： 
 * 	class field上的泛型信息
 * 	继承时指定的泛型信息
 */
public class GenericTest {
	@Test
	public void test_field_generics() throws SecurityException, NoSuchFieldException {
		Type type=Foo.class.getField("bar").getGenericType();

		assertTrue(type instanceof ParameterizedType);
		assertFalse(type instanceof Class);

		ParameterizedType pt=(ParameterizedType)type;

		assertTrue(pt.getActualTypeArguments().length == 2);
		assertEquals(String.class,pt.getActualTypeArguments()[0]);
		assertEquals(String.class,pt.getActualTypeArguments()[1]);
		assertEquals(Map.class,pt.getRawType());
	}

	@Test
	public void test_inheritance_generics() {
		SubType sub=new SubType();
		Type superclass=sub.getClass().getGenericSuperclass();
		ParameterizedType parameterized=(ParameterizedType)superclass;
		assertEquals(1,parameterized.getActualTypeArguments().length);
		assertEquals(String.class,parameterized.getActualTypeArguments()[0]);
	}

	class Foo {
		public Map<String, String>	bar;
	}

	class SuperType<T> {}

	class SubType extends SuperType<String> {}
}
