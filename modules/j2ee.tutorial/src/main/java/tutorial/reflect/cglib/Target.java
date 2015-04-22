package tutorial.reflect.cglib;

public class Target {

	public String execute() {
		String message="----------test()----------";
		System.out.println(message);
		return message;
	}
}