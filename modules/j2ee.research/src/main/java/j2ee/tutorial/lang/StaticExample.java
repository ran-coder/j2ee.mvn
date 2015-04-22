package j2ee.tutorial.lang;

/**
 * @author yuanwei
 * @version ctreateTime:2011-6-29 上午11:01:43
 */
public class StaticExample {
	private static int	i	=100;
	private static int	a	=i+1;
	final static int	b	=i+2;

	static{
		System.out.println("static:i="+i+",a="+a);
	}

	public int getNext() {
		return i++;
	}

	public static void main(String[] args) {
		System.out.println(a);
		StaticExample test=new StaticExample();
		System.out.println(test.getNext());
		StaticExample testObject=new StaticExample();
		System.out.println(testObject.getNext());
		System.out.println(test.getNext());
	}

}
