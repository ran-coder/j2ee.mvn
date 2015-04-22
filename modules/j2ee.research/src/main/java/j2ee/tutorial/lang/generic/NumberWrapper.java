package j2ee.tutorial.lang.generic;

public interface NumberWrapper<T extends Number> {
	public double square(T num);
}
