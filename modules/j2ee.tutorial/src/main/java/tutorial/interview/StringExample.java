package tutorial.interview;

public class StringExample {
	public static final String	A	="ab";	// 常量A
	public static final String	B	="cd";	// 常量B
	public static final String	C;	// 常量A
	public static final String	D;	// 常量B
	static{
		C="ab";
		D="cd";
	}
	static void test1(){
		StringBuilder str=new StringBuilder("111");
		StringBuilder str1=str;
		System.out.println(str1 == str);// true
		str1.append("222");
		System.out.println(str1 == str);// true
		String str3="111";
		String str4=str3;
		str4="222";
		System.out.println(str3 == str4);
		System.out.println("*****************");
		String s5="555";
		String s6=new String("555");
		System.out.println(s5==s6);

		System.out.println("*****************");
		String s=A + B; // 将两个常量用+连接对s进行初始化
		String t="abcd";
		if(s == t){
			System.out.println("s等于t，它们是同一个对象");
		}else{
			System.out.println("s不等于t，它们不是同一个对象");
		}
		System.out.println("*****************");
		s=C + D; // 将两个常量用+连接对s进行初始化
		t="abcd";
		if(s == t){
			System.out.println("s等于t，它们是同一个对象");
		}else{
			System.out.println("s不等于t，它们不是同一个对象");
		}
	}
	static void test2(){
		StringBuilder str=new StringBuilder("111");
		StringBuilder str1=str;
		System.out.println(str1 == str);// true
		str1.append("222");
		System.out.println(str1 == str);// true

		int swi=2;
		switch(swi){
		case 1:
			System.out.println(1);
		case 2:
			System.out.println(2);
		case 3:
			System.out.println(3);
		default:
			System.out.println(4);
		}

		String x="hello";// stack String池
		String y="world";// stack String池
		String z=new String("helloworld");// heap区
		String a="helloworld";// stack String池
		System.out.println("x+y equals z:" + (x + y).equals(z));// true
		System.out.println("a == z:" + (a == z));// false
		System.out.println("x == hello:" + (x == "hello"));// true
		/*
		 * 因为+号的作用是返回另外一个新建的String对象
		 * 只有使用引号包含文本的方式创建的String对象之间使用“+”连接产生的新对象才会被加入字符串池中。
		 * 对于所有包含new方式新建对象（包括null）的“+”连接表达式，它所产生的新对象都不会被加入字符串池中
		 */
		System.out.println("a == hello + world:" + (a == ("hello" + "world")));// stack
																				// String池
																				// true
		System.out.println("z == x+y:" + (z == (x + y)));// 新建的String false
		System.out.println("a == x+y:" + (a == (x + y)));// false

		double i=5.0;
		double j=1 / 4 + 3 / 4 + i + 12 / 6.0 + 3 / 4 + 1 / 4;
		System.out.println(j);

		Integer m=300;
		Integer n=300;
		System.out.println(m == n);// false，the different reference
		m=30;
		n=30;
		System.out.println(m == n);// true, the same reference
	}

	public static void main(String[] args) {
		test1();
		test2();
	}
}
