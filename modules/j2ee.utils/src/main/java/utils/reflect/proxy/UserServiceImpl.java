package utils.reflect.proxy;

public class UserServiceImpl implements IUserService{
	public void addUser(int userid) {
		System.out.println("IUserService.addUser("+userid+");");
	}

}
