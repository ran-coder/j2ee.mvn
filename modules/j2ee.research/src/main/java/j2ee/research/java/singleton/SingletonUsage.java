package j2ee.research.java.singleton;

import utils.test.benchmark.Benchmarks;
import utils.test.benchmark.Task;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 2013-12-16 17:43
 * To change this template use File | Settings | File Templates.
 */
public class SingletonUsage{
	static void print(Object object){}
	public static void main(String[] args){
		EnumSingleton.createString();//1)使用单例的静态方法时,会触发创建instance,同(2)
		Singleton.createString();//2)使用单例的静态方法时,会触发创建instance                                速度快,不同步
		LazySingleton.createString();//3)使用单例的静态方法时,不会触发创建instance,但是同步耗时,速度慢        速度慢,同步
		SingletonByInnerStaticClass.createString();//4)兼具2),3)有点                                       速度快,同步
		System.out.println("********************* 测速 *********************");


		int nThreads=1;
		int count=100000;
		Benchmarks.runTask(nThreads,count,null,
			new Task("EnumSingleton"){public void run(){print(EnumSingleton.v1);}},
			new Task("Singleton"){public void run(){print(Singleton.getInstance());}},
			new Task("LazySingleton"){public void run(){print(LazySingleton.getInstance());}},
			new Task("SingletonByInnerStaticClass"){public void run(){print(SingletonByInnerStaticClass.getInstance());}}
		);

	}
}
