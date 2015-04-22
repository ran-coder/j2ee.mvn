package j2ee.research.java.thread.threadlocal;


public class NamedThreadLocal<T> extends ThreadLocal<T> {

	private final String name;


	/**
	 * Create a new NamedThreadLocal with the given name.
	 * @param name a descriptive name for this ThreadLocal
	 */
	public NamedThreadLocal(String name) {
		if(name==null)name="name"+hashCode();
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}

}
