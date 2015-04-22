package j2ee.tutorial.google.guava.io;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 2013-11-15 14:54
 * To change this template use File | Settings | File Templates.
 */
public class ResourcesExample{
	public static void print(String... arrays){
		if(arrays!=null&&arrays.length>0){
			StringBuilder builder=new StringBuilder();
			for(String str:arrays){
				builder.append(str);
			}
			System.out.println(builder);
		}

	}
	public static void main(String[] args) throws IOException{
		//print(ResourcesExample.class.getResource("./test.data").toString());
		System.out.println(new File(".").getAbsolutePath());
		System.out.println(new File("/").getAbsolutePath());
		System.out.println(new File("./").getAbsolutePath());
		System.out.println(ResourcesExample.class.getResource(".").toString());
		System.out.println(ResourcesExample.class.getResource("/").toString());
		System.out.println(ResourcesExample.class.getResource("./").toString());
		System.out.println(ResourcesExample.class.getResource("ResourcesExample.class").toString());

		//System.out.println(Resources.getResource(ResourcesExample.class,"test.data"));
		//print(Resources.toString(Resources.getResource("./j2ee/tutorial/google/guava/io/test.data/test.data"), Charsets.UTF_8));
	}
}
