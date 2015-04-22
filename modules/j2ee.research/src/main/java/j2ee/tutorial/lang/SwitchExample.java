package j2ee.tutorial.lang;
/**  
 * @author yuanwei  
 * @version ctreateTime:2012-2-6 下午3:48:10
 *   
 */
public class SwitchExample {

	public static void main(String[] args) {
		int i=100;
		switch(i){
		case 0:
		case 100:
			System.out.println(i);
			break;
		default:
			System.out.println("default");
			break;
		}
		byte b=0x2;
		switch(b){
		case 0:break;
		case 2:
			System.out.println(b);
		default:
			System.out.println("default");
			break;
		}
		char c=5;
		switch(c){
		case 0:break;
		case 5:
			System.out.println(c);
		default:
			System.out.println("default");
			break;
		}
		short s=2;
		switch(s){
		case 0:break;
		}
	}

}
