package j2ee.server.jetty.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyServer {
	public static void main(String[] args) throws Exception {
		new ClassPathXmlApplicationContext("/jetty/spring/spring.xml");
	}
}
