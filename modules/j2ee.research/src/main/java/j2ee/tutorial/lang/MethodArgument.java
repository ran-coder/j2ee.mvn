package j2ee.tutorial.lang;

public class MethodArgument {
	public static void changeValue(int i){
		System.out.println("changeValue(int)");
		i=1;//方法外i的值不变
	}
	public static void changeValue(Integer i){
		System.out.println("changeValue(Integer)");
		i=1;//方法外i的值不变
	}
	public static void changeBean(Bean bean){
		bean.setName("change");
	}

	public static void main(String[] args) {
		int i=0;
		changeValue(Integer.valueOf(i));//System.out.println(i);
		changeValue(Integer.valueOf(i).intValue());//System.out.println(i);
		System.out.println(i);
		Bean bean=new Bean();
		bean.setName("0");
		changeBean(bean);
		System.out.println(bean);
	}
	public static class Bean{
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name=name;
		}

		/** toJsonString */
		public String toString(){
			StringBuilder retValue = new StringBuilder();
			retValue.append("{")
				.append("\"name\" : \"").append(this.name).append("\",")
				;
			if(retValue.length()>0)retValue.setLength(retValue.length()-1);
			return retValue.append("}").toString();
		}
	}
}
