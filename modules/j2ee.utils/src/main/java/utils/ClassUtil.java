package utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ClassUtil {
	public static void sleep(long sleepTime) {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
		}
	}

	public static void sleep(String str, long sleepTime) {
		try {
			System.out.println(str+" time:"+sleepTime);
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
		}
	}
	public static void sleepInline(long sleepTime) {
		try {
			System.out.print('.');
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
		}
	}

	/*
	 * 实例化指定类 classname 类全名(带包)
	 */
	public static Object getClassInstance(String classname) {
		Object obj = null;
		try {
			obj = Class.forName(classname).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	public static void getString(Object obj) throws ClassNotFoundException{
		Class<? extends Object> clazz=obj.getClass();
		Field[] fields=clazz.getFields();
		for (int i = 0; i < fields.length; i++) {
			System.out.println(fields[i].getName());
		}
		System.out.println("===============");
		for(Method m:clazz.getMethods()){
			System.out.println(m.getName()+":"+clazz.getCanonicalName());
		}
		System.out.println("===============");
		for(Field f:clazz.getFields()){
			System.out.println(f.getName());
		}
	}
	public static String getMethod(String name){
		return "";
	}
	public static void totalMemory(){
		//System.out.println("maxMemory:"+Runtime.getRuntime().maxMemory()/1024/1024+"M");
		System.out.println("totalMemory:"+(double)Runtime.getRuntime().totalMemory()/1024D/1024D+"M");
	}
	public static void printMemory(){
		System.out.println(
			(double)(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/(1024)
			+"	"+(double)Runtime.getRuntime().totalMemory()/(1024)
			+"	"+(double)(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())
			/Runtime.getRuntime().totalMemory()
		);
	}
	
	public static String showBean(Object bean, boolean showEmpty,boolean showClassName,String packagePath) {
		if(bean == null) return null;
		StringBuilder sb=null;
		if(showClassName)
			sb=new StringBuilder(bean.getClass().getName()).append("[");
		else
			sb=new StringBuilder(bean.getClass().getSimpleName()).append("[");

		sb.append("\"hashCode\":\"").append(bean.hashCode()).append("\",");
		try{
			BeanInfo bi=Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[] pd=bi.getPropertyDescriptors();
			for(int i=0;i < pd.length;i++){
				if(!"class".equals(pd[i].getName())){
					Object result=pd[i].getReadMethod().invoke(bean);
					if(showEmpty || (result != null && result.toString().trim().length()>0)  ){
						if(result.getClass().getName().startsWith("java.")){
							sb.append(pd[i].getDisplayName()).append("=").append("\"").append(result).append("\"");
						}else{
							//System.out.println("showBean:"+bean.getClass().getName()+"	"+result.getClass().getName());
							if( bean.getClass().getName().startsWith(packagePath))
								sb.append("\n	").append( showBean(result,showEmpty,showClassName,packagePath) ).append("\n");
						}
						//sb.append(pd[i].getDisplayName()).append("=").append( showBean(result,showNulls,showClassName) );
						if(i == pd.length - 1) continue;
						sb.append(",");
					}
				}
			}
		}catch(Exception ex){
			ex.fillInStackTrace();
		}

		if(sb.length()>1)sb.setLength(sb.length()-1);
		return sb.append("]").toString();
	}
	public static String showBean(Object bean, int showEmpty,int showClassName){
		return showBean(bean,showEmpty==1,showClassName==1,"");
	}
	public static void showBean(Object bean){
		System.out.println(showBean(bean,false,false,""));
	}
	public static void toString(Object bean,String packagePath){
		System.out.println(showBean(bean,false,false,packagePath));
	}
	/** obj==null */
	public static boolean ifNull(Object obj){
		return obj==null;
	}
	/** 有一个为null返回true */
	public static boolean ifAnyNull(Object... objs){
		if(objs==null||objs.length<1)return false;
		for(Object obj:objs){
			if(obj==null)return true;
		}
		return false;
	}
	/** 有一个不为null返回true */
	public static boolean ifAnyNotNull(Object... objs){
		if(objs==null||objs.length<1)return false;
		for(Object obj:objs){
			if(obj!=null)return true;
		}
		return false;
	}
	public static void main(String[] args) throws ClassNotFoundException {
		getString(new User("admin","admin888"));
		Object o1=null,
			o2=new Object(),
			o3=null
				;
		
		System.out.println(ifAnyNull(o1,o2,o3));
	}
}
