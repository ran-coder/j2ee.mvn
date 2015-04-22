package utils.bean;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Vector;

/**
 * BasePojo Pojo(实体)基类
 * http://www.popo4j.com/article/equals-hashcode-tostring-method-pojo-base-class.html
 */
public class BaseBean implements Serializable {
	private static final long	serialVersionUID	=-646575756765675L; // 版本序列号

	/**
	 * 指示其他某个对象是否与此对象“相等”
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return this == null;
		// 自身比较
		if(obj == this){ return true; }
		// 类型相同
		if(obj.getClass() == this.getClass()){
			// 当前类反射方法组
			Method[] thisMethodGroup=this.getClass().getMethods();
			try{
				// 遍历反射方法组并提取当前类属性的getter方法
				for(Method method:thisMethodGroup){
					// 过滤与当前类属性无关的get方法
					if(method.getName().startsWith("get") && !method.getName().equals("getClass")){
						// 将当前类属性的getter方法与比较类属性的getter方法值作比较
						Method currentMethod=obj.getClass().getMethod(method.getName());
						// 执行方法以获取返回值比较(关键点：注意参数不相同)
						Object objReturnValue=currentMethod.invoke(obj);
						Object thisReturnValue=method.invoke(this);
						// 空值报异
						if(objReturnValue == null){
							System.err.println("异常信息：类" + obj.getClass().getName() + "中的" + currentMethod.getName()
									+ "方法为null值！无法进行对象比较！");
						}
						if(thisReturnValue == null){
							System.err.println("异常信息：类" + this.getClass().getName() + "中的" + method.getName()
									+ "方法为null值！无法进行对象比较！");
						}
						if(objReturnValue==null)return thisReturnValue==null;
						// 返回值不相等则返回逻辑假
						if(!objReturnValue.equals(thisReturnValue)){ return false; }
					}
				}
			}catch(SecurityException ex){
				System.err.println("异常信息：参数错误，安全管理器检测到安全侵犯！\r\n" + ex.getMessage());
			}catch(NoSuchMethodException ex){
				System.err.println("异常信息：参数错误，无法找到某一特定的方法！\r\n" + ex.getMessage());
			}catch(IllegalArgumentException ex){
				System.err.println("异常信息：参数错误，向方法传递了一个不合法或不正确的参数！\r\n" + ex.getMessage());
			}catch(IllegalAccessException ex){
				System.err.println("异常信息：参数错误，对象定义无法访问，无法反射性地创建一个实例！\r\n" + ex.getMessage());
			}catch(InvocationTargetException ex){
				System.err.println("异常信息：参数错误，由调用方法或构造方法所抛出异常的经过检查的异常！\r\n" + ex.getMessage());
			}
		}
		// 通过不相等比较则返回逻辑真
		return true;
	}

	/**
	 * 返回该对象的哈希码值
	 */
	@Override
	public int hashCode() {
		// 生成简单的位运算hash散列码
		String key=this.toString();
		int prime=key.hashCode();
		int hash=prime;
		for(int i=0;i < key.length();i++){
			hash^=(hash << 23 >> 17) ^ key.charAt(i) * 13131;
		}
		// 返回结果
		return (hash % prime) * 33;
	}

	/**
	 * 返回该对象的字符串表示(类似数组的toString方法输出结果)
	 */
	@Override
	public String toString() {
		// 当前类反射方法组
		Method[] methodGroup=this.getClass().getMethods();
		// 保存内容
		StringBuffer content=new StringBuffer("[");
		// 保存属性的getter方法组
		List<Method> getMethodGroup=new Vector<Method>();
		try{
			// 遍历反射方法组并提取属性的getter方法
			for(Method method:methodGroup){
				// 过滤与属性无关的get方法
				if(method.getName().startsWith("get") && !method.getName().equals("getClass")){
					// 保存属性的getter方法
					getMethodGroup.add(method);
				}
			}
			// 处理仅包含属性的getter方法
			for(int i=0;i < getMethodGroup.size();i++){
				// 执行get方法并拼接获取到的返回值(如果底层方法返回类型为 void，则该调用返回 null)
				content
						.append(getMethodGroup.get(i).invoke(this)
								+ ((i < getMethodGroup.size() - 1) ? ",\u0020" : "]"));
			}
		}catch(IllegalAccessException ex){
			System.err.println("异常信息：参数错误，对象定义无法访问，无法反射性地创建一个实例！\r\n" + ex.getMessage());
		}catch(IllegalArgumentException ex){
			System.err.println("异常信息：参数错误，向方法传递了一个不合法或不正确的参数！\r\n" + ex.getMessage());
		}catch(InvocationTargetException ex){
			System.err.println("异常信息：参数错误，由调用方法或构造方法所抛出异常的经过检查的异常！\r\n" + ex.getMessage());
		}
		// 返回结果
		return content.toString();
	}
}
