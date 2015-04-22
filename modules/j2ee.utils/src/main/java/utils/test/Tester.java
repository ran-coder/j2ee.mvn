package utils.test;
/**  
 * @author yuanwei  
 * @version ctreateTime:2011-10-25 上午9:33:33
 * 打印时间是总时间
 */
public abstract class Tester {
	/** nanoseconds */
	public final static int NS=0;
	/** milliseconds */
	public final static int MS=1;
	/** seconds */
	public final static int SEC=2;
	
	private String name;
	public Tester(String name){
		this.name=name;
	}
	public void run(){
		run(SEC);
	}
	public void run(int type){
		try{
			long start=System.nanoTime();
			test();
			if(type==SEC){
				System.out.format("%2$.6f	%1$s SEC\n",name,(System.nanoTime()-start)/1.0e9);
			}else if(type==MS){
				System.out.format("%2$.4f	%1$s MS\n",name,(System.nanoTime()-start)/1.0e6);
			}else if(type==NS){
				System.out.format("%2$d	%1$s NS\n",name,(System.nanoTime()-start));
			}
			//long start=System.nanoTime();
			//test();
			//System.out.format("%1$s:%2$.4f ms\n",name,(System.nanoTime()-start)/1.0e9);
			///System.out.format("%2$d:%1$s-ns\n",name,(System.nanoTime()-start));
			//System.out.format("%2$.6f:%1$s-sec\n",name,(System.nanoTime()-start)/1.0e9);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public abstract void test() throws Exception;
}
