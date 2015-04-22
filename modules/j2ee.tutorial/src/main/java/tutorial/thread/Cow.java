package tutorial.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**  
 * @author yuanwei  
 * @version ctreateTime:2011-7-18 上午11:17:57
 *   
 */
public class Cow implements Runnable {
	private int age;
	private int year;
	//private List<Cow> children;
	private static AtomicInteger atomicInteger=new AtomicInteger(1);
	public Cow(int age,int year){
		this.age=age;
		this.year=year;
		//this.children=new ArrayList<Cow>();
		System.out.println(atomicInteger.getAndIncrement());
	}
	public void run() {
		Cow crow=null;
		while(year<20){
			year++;
			if(age>=5){
				crow=new Cow(0,year);
				//children.add(crow);
				new Thread(crow).start();
			}
			age++;
		}
	}
	public static void main(String[] args) {
		new Thread(new Cow(5,1)).start();
	}
}
