package j2ee.tutorial.apache.util.beanutils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public class BeanUtilsTest {
	public static void copyVO(){
		TeacherForm form=new TeacherForm(1,"TeacherForm");
		Teacher teacher=new Teacher();
		try {
			BeanUtils.copyProperties(teacher,form);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		System.out.println(teacher.toString());
	}
	public static void copyMap2Object() throws IllegalAccessException, InvocationTargetException{
		Map<String,String[]> map=new HashMap<String,String[]>();
		map.put("id",new String[]{"1"});
		map.put("username",new String[]{"name1"});
		Teacher teacher=new Teacher();
		BeanUtils.copyProperties(teacher,map);
		System.out.println(teacher);
	}
	public static void main(String[] args) throws Exception {
		//copyVO();
		copyMap2Object();
	}

}
