package utils.reflect.proxy;

public class AOPFactory {
	private static Object getClassInstance(String clzName) {
		Object obj = null;
		try {
			Class<?> cls = Class.forName(clzName);
			obj = (Object) cls.newInstance();
		} catch (ClassNotFoundException cnfe) {
			System.out.println("ClassNotFoundException:" + cnfe.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	public static Object getProxy(BaseHandler handler,String clzName) {
		Object proxy = null;
		Object obj = getClassInstance(clzName);
		if (obj != null) {
			proxy = handler.bind(obj);
		} else {
			System.out.println("Can't get the proxyobj");
		}
		return proxy;
	}
	
	public static void main(String[] args){
		IUserService service=(IUserService)AOPFactory.getProxy(
				new ServiceHandler(),"webutil.reflect.proxy.UserServiceImpl");
		service.addUser(1001);
	}
}
