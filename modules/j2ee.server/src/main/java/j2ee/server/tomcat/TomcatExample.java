package j2ee.server.tomcat;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;


/**
 * @author yuanwei
 * @version ctreateTime:2012-4-10 下午4:11:20
 * http://www.ibm.com/developerworks/cn/java/j-lo-servlet/
 */
public class TomcatExample {
	public static void step1() {
		Tomcat tomcat=new Tomcat();
		//File appDir=new File(tomcat.,"webapps/examples");
		tomcat.addWebapp(null,"/examples","");
		try{
			tomcat.start();
		}catch(LifecycleException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//ByteChunk res=getUrl("http://localhost:/examples/servlets/servlet/HelloWorldExample");
		//System.out.println(res.toString().indexOf("<h1>Hello World!</h1>") > 0);
	}
	public static void main(String[] args) {
		
	}
}
