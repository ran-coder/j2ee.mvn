package j2ee.tutorial.apache.util.pool;

import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import org.apache.commons.pool.BaseKeyedPoolableObjectFactory;

/**
 * Created by IntelliJ IDEA.
 *
 * @author leizhimin 2009-10-27 17:46:18
 */
public class GenericKeyedObjectPoolTest {
	public static void main(String[] args) throws Exception {
		//创建一个对象池
		GenericKeyedObjectPool pool=new GenericKeyedObjectPool(new BaseKeyedPoolableObjectFactory() {
			@Override
			public Object makeObject(Object o) throws Exception {
				if(o != null && o instanceof User) return o;
				else return null;
			}
		});

		pool.setConfig(PoolUtil.initGenericKeyedObjectPoolConfig());
		//添加对象到池，重复的不会重复入池
		pool.addObject("a");
		pool.addObject("b");
		pool.addObject("x");
		pool.addObject(null);
		pool.addObject(null);
		pool.addObject(null);
		pool.addObject(new User("zhangsan","123"));
		pool.addObject(new User("lisi","112"));
		pool.addObject(new User("lisi","112",32));
		pool.addObject(new User("lisi","112",32,"一个烂人！"));

		System.out.println(pool.borrowObject("a"));
		//清除最早的对象
		pool.clearOldest();

		//获取并输出对象
		User u1=new User("lisi","112",32,"一个烂人！");
		System.out.println(pool.borrowObject(u1));
		pool.returnObject(u1,u1);

		//获取并输出对象
		User u2=new User("lisi","112",32,"一个烂人！");
		System.out.println(pool.borrowObject(u2));
		pool.returnObject(u2,u2);

		//获取并输出对象
		User u3=new User("lisi","112",32);
		System.out.println(pool.borrowObject(u3));
		pool.returnObject(u3,u3);

		//获取并输出对象
		User u4=new User("lisi","112");
		System.out.println(pool.borrowObject(u4));
		pool.returnObject(u4,u4);

		System.out.println(pool.borrowObject(u4));
		pool.returnObject(u4,u4);
		System.out.println(pool.borrowObject(u4));
		pool.returnObject(u4,u4);
		System.out.println(pool.borrowObject(u4));
		pool.returnObject(u4,u4);
		System.out.println(pool.borrowObject(u4));
		pool.returnObject(u4,u4);
		System.out.println(pool.borrowObject(u4));
		pool.returnObject(u4,u4);
		System.out.println(pool.borrowObject(u4));
		pool.returnObject(u4,u4);
		System.out.println(pool.borrowObject(u4));
		pool.returnObject(u4,u4);

		System.out.println(pool.borrowObject(new User("lisi","112")));
		//                System.out.println(pool.borrowObject(new User("lisi", "112")));

		//输出池状态
		System.out.println(pool.getMaxIdle());
		System.out.println(pool.getMaxActive());
		pool.clearOldest();
		pool.close();

	}
}

class User {
	private String	name;
	private String	pswd;
	private int		age;
	private String	reamark;

	User() {}

	User(String name, String pswd) {
		this.name=name;
		this.pswd=pswd;
	}

	User(String name, String pswd, int age) {
		this.name=name;
		this.pswd=pswd;
		this.age=age;
	}

	User(String name, String pswd, int age, String reamark) {
		this.name=name;
		this.pswd=pswd;
		this.age=age;
		this.reamark=reamark;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name=name;
	}

	public String getPswd() {
		return pswd;
	}

	public void setPswd(String pswd) {
		this.pswd=pswd;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age=age;
	}

	public String getReamark() {
		return reamark;
	}

	public void setReamark(String reamark) {
		this.reamark=reamark;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;

		User user=(User)o;

		if(!name.equals(user.name)) return false;
		if(!pswd.equals(user.pswd)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result=name.hashCode();
		result=31 * result + pswd.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "User{" + "name='" + name + '\'' + ", pswd='" + pswd + '\'' + ", age=" + age + ", reamark='" + reamark
				+ '\'' + '}';
	}
}