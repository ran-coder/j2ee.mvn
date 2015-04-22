package tutorial.lang;

public class IntegerExample {
	public static void main(String[] args) {
		System.out.println(System.getProperty("java.lang.Integer.IntegerCache.high"));
		int i=127, j=127;
		System.out.println(i == j);
		i=128;
		j=128;
		System.out.println(i == j);
		i=Integer.valueOf(1280);
		j=1280;
		System.out.println(i == j);
		i=Integer.valueOf(1280);
		j=Integer.valueOf(1280);
		System.out.println(i == j);
		i=new Integer(1280);
		j=new Integer(1280);
		System.out.println(i == j);
		
		Integer m=127, n=127;
		System.out.println(m == n);
		m=128;
		n=128;
		System.out.println(m == n);
		m=Integer.valueOf(1280);
		n=1280;
		System.out.println(m == n);
		m=Integer.valueOf(1280);
		n=Integer.valueOf(1280);
		System.out.println(m == n);
		m=new Integer(1280);
		n=new Integer(1280);
		System.out.println(m == n);
		
		int a=0;
		for(int b=0;b < 100;b++){
			a=a++;
		}
		System.out.println(a);
	}
	
}
