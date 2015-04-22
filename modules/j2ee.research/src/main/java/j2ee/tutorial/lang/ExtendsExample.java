package j2ee.tutorial.lang;

/**
 * @author yuanwei
 * @version ctreateTime:2011-7-20 上午10:14:21
 */
public class ExtendsExample {
	static class M {
		void print() {
			System.out.println("M");
		}
		static void p(){
			System.out.println("M.p");
		}
	}

	static class N extends M {
		void print() {
			System.out.println("N");
		}
		static void p(){
			System.out.println("N.p");
		}
	}

	public static void main(String[] args) {
		M m=new N();
		m.print();
		M.p();
	}
}
