package j2ee.tutorial.apache.util.lang;

import java.util.Date;


public class Test {
	public static void main(String[] args) {
		System.out.println(new Person("name",20,new Date()).toString());
	}
}
