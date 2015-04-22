package tutorial.lang.exception;

public class TestFinally {
	public static void main(String[] args) {
		System.out.println(foo(-1));
		System.out.println(foo(1));
		System.out.println(test1());
		System.out.println(testPerson(new Person()));
	}

	@SuppressWarnings("finally")
	private static boolean foo(int value) {
		try{
			if(value < 0){
				return false;
			}else{
				return true;
			}
		}finally{
			return false;
		}
	}

	static int	val;
	static int test() {
		val=1;
		try{
			return val;
		}finally{
			val=2;
		}
	}

	public static int test1() {
		int j=0;
		try{
			j=1;
			System.out.println("try:" + j);
			return j;
		}catch(Exception e){

		}finally{
			j=2;
			System.out.println("finally:" + j);
		}
		return j;
	}

	public static Person testPerson(Person p) {
		try{
			p.name="try";
			return p;
		}catch(Exception e){
		}finally{
			p.name="finally";
		}

		p.name="last";
		return p;
	}

	static class Person {
		String	name;
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
