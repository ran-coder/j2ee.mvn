package tutorial.lang;

/**
 * @author yuanwei
 * @version ctreateTime:2011-6-29 上午11:01:43
 */
public class StaticInitExample {
	/*
	 * 1.初始化为默认值 2.设置为=后面的值 3.设置static{}块的值 相当于: private final static
	 * SimpleSingleton1 simple; public static int counter1; public static int
	 * counter2; static{ simple=new SimpleSingleton1(); counter2=0; }
	 */
	static class SimpleSingleton1 {
		private final static SimpleSingleton1	simple		=new SimpleSingleton1();
		public static int						counter1;
		public static int						counter2	=0;

		private SimpleSingleton1() {
			counter1++;
			counter2++;
		}

		public static SimpleSingleton1 getInstance() {
			return simple;
		}
	}

	static class SimpleSingleton2 {
		public static int						counter1;
		public static int						counter2	=0;
		private final static SimpleSingleton2	simple		=new SimpleSingleton2();

		private SimpleSingleton2() {
			counter1++;
			counter2++;
		}

		public static SimpleSingleton2 getInstance() {
			return simple;
		}
	}

	public static void main(String[] args) {
		System.out.println("SimpleSingleton1:counter1=" + SimpleSingleton1.counter1 + ",counter2=" + SimpleSingleton1.counter2);
		System.out.println("SimpleSingleton2:counter1=" + SimpleSingleton2.counter1 + ",counter2=" + SimpleSingleton2.counter2);

	}
}