package tutorial.apache.util.lang;

import org.apache.commons.lang.time.StopWatch;

public class StopWatchTest {

	public static void main(String[] args) {
		StopWatch clock=new StopWatch();

		System.out.println("Time to Math.sin(0.34) ten million times?");
		clock.start();
		for(int i=0;i < 100000000;i++){
			Math.sin(0.34);
		}
		clock.stop();
		System.out.println("It takes " + clock.getTime() + " milliseconds");
		// System.out.printf("It takes %s milliseconds.",clock.getTime());
		clock.reset();
		System.out.println("How long to multiply 2 doubles one billion times?");
		clock.start();
		@SuppressWarnings("unused")
		double result;
		for(int i=0;i < 1000000000;i++){
			result=3423.2234 * 23e-4;
		}
		clock.stop();
		System.out.println("It takes " + clock.getTime() + " milliseconds.");

		/*System.out.println("************ Testing the split( ) method. ************ ");
		clock.reset();
		clock.start();
		try{
			Thread.sleep(1000);
		}catch(Exception e){
		}
		clock.split();
		System.out.println("Split Time after 1 sec: " + clock.getTime());
		try{
			Thread.sleep(1000);
		}catch(Exception e){
		}
		System.out.println("Split Time after 2 sec: " + clock.getTime());
		clock.unsplit();
		try{
			Thread.sleep(1000);
		}catch(Exception e){
		}
		System.out.println("Time after 3 sec: " + clock.getTime());*/

	}

}
