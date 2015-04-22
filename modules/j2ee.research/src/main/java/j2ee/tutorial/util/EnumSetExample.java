package j2ee.tutorial.util;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

/**
 * 老紫竹JAVA提高教程(5)-认识Set集合之EnumSet。<br>
 * 枚举 set 中所有键都必须来自单个枚举类型<br>
 * 该枚举类型在创建 set 时显式或隐式地指定<br>
 * 迭代顺序是声明时的顺序。<br>
 * 不允许null数据。
 * @author 老紫竹 JAVA世纪网(java2000.net)
 */
public class EnumSetExample {
	public static void main(String[] args) {
		// 创建一个指定类型的空的集合
		EnumSet<MyEnum> set=EnumSet.noneOf(MyEnum.class);
		set.add(MyEnum.RED);
		set.add(MyEnum.GREEN);
		set.add(MyEnum.BLUR);
		showSet(set);
		
		// 创建指定类型的所有数据的集合
		EnumSet<MyEnum> set2=EnumSet.allOf(MyEnum.class);
		showSet(set2);
		
		// 创建指定类型指定初始数据的集合
		EnumSet<MyEnum> set3=EnumSet.of(MyEnum.GREEN,MyEnum.RED,MyEnum.WHITE);
		showSet(set3);
		
		// 创建指定类型，指定范围的集合
		// 包含边界数据
		EnumSet<MyEnum> set4=EnumSet.range(MyEnum.RED,MyEnum.YELLOW);
		showSet(set4);
		
		// 集合的用法和普通的没有区别
	}
	
	/**
	 * 显示Set里面的数据。
	 * @param set
	 */
	private static void showSet(Set<MyEnum> set) {
		System.out.println(Arrays.toString(set.toArray()));
	}
}

enum MyEnum {
	BLACK, WHITE, RED, BLUR, GREEN, YELLOW
}
