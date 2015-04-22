package tutorial.lang;

public class NumberCase {

	public static void main(String[] args) {
		int x = 2;
		int y = 5;
		//// Wrong: yields result 0.0
		System.out.println("x / y:"+(x / y));

		// Right: yields result 0.4
		System.out.println("x / (double) y:"+(x / (double) y));
		System.out.println("(double)x /  y:"+((double)x /  y));
		System.out.println("x * (double) y:"+(x * (double) y));
		
	}

}
