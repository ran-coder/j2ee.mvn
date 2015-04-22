package utils.reflect;

import java.lang.reflect.Method;

public class Test {
	public static void print(String s){
		System.out.println(s);
	}
	
	public static Object invokeMethod(Object obj, String methodName, Object[] args) throws Exception {
		Class<? extends Object> ownerClass = obj.getClass();
		@SuppressWarnings("rawtypes")
		Class[] argsClass = new Class[args.length];
		for (int i = 0, j = args.length; i < j; i++) {
			argsClass[i] = args[i].getClass();
		}
		Method method = ownerClass.getMethod(methodName, argsClass);
		return method.invoke(obj, args);
	}

	public static void main(String[] args) throws Exception{
		//getMethod 必须是public
		//getDeclaredMethod 都可
		Method mt=Test.class.getDeclaredMethod("print",String.class);
		//static时第一个参数可以为null
		mt.invoke(new Test(),"1");
		//invokeMethod(Test.class,"print",new Object[]{"1"});
		StringBuffer.class.getDeclaredMethod("append", String.class).invoke(new StringBuffer(), "123");

	}
}
