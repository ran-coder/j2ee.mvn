package utils.thread;

import utils.ClassUtil;
import utils.RandomUtil;


public class BlackListExample<T> {

	public static void main(String[] args) {
		final BlackList<String> list=new BlackList<String>("test");
		Runnable task=new Runnable() {
			public void run() {
				String temp=null;
				//long start=0;
				for(int i=0;i < 1000000;i++){
					temp=RandomUtil.getRandomChars64(5);
					list.add(temp);
					//start=System.currentTimeMillis();
					list.contains(temp);
					//System.out.println(System.currentTimeMillis()-start);
					ClassUtil.sleep(100);
				}
				
			}
		};
		new Thread(task).start();
		new Thread(task).start();
		new Thread(task).start();
		new Thread(task).start();
		ClassUtil.sleep(100000000);
	}
}
