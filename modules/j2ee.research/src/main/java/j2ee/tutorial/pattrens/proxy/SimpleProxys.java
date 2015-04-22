package j2ee.tutorial.pattrens.proxy;
/**  
 * @author yuanwei  
 * @version ctreateTime:2011-7-8 下午5:23:41
 *   
 */
public class SimpleProxys {
	public static <T> T newProxyInstance(Class<T> clazz){
		try{
			//clazz.getConstructors();
			//clazz.getConstructor(parameterTypes).newInstance();
			return clazz.newInstance();
		}catch(InstantiationException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IllegalAccessException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
