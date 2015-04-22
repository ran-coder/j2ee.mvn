package tutorial.lang;

/**
 * @author yuanwei
 * @version ctreateTime:2011-6-3 下午05:08:07
 */
public class AsciiUtil {
	public static void main(String[] args) {
		//1-27是控制字符，28-127是字符
		char a=1;
		while(a < 128){
			a++;
			System.out.print((int)a+"	"+a+"	");
			a++;
			System.out.print((int)a+"	"+a+"	");
			a++;
			System.out.print((int)a+"	"+a+"	");
			a++;
			System.out.print((int)a+"	"+a+"	");
			a++;
			System.out.print((int)a+"	"+a+"	");
			System.out.println();
		}
	}
}
