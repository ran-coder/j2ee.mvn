package j2ee.tutorial.apache.util.pool.example;

/**
 * @author yuanwei
 * @version ctreateTime:2011-5-12 下午02:25:48
 */
public class BigObject {
	private boolean	active;

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active=active;
	}

	public BigObject() {
		active=true;
		System.out.println("make new BigObject ");
	}

	public String toString() {
		return "BigObject-" + this.hashCode();
	}

}