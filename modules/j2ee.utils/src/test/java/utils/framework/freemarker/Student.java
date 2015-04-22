package utils.framework.freemarker;
/**  
 * @author yuanwei  
 * @version ctreateTime:2012-7-20 下午4:47:12
 *   
 */
public class Student {
	private String	stuNo;
	private String	stuName;
	/* == 两个属性的setter和getter略 == */
	public Student() {}
	
	public Student(String stuNo, String stuName) {
		this.stuNo=stuNo;
		this.stuName=stuName;
	}

	public String getStuNo() {
		return stuNo;
	}

	public void setStuNo(String stuNo) {
		this.stuNo=stuNo;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName=stuName;
	}

	@Override
	public int hashCode() {
		final int prime=31;
		int result=1;
		result=prime * result + ((stuName == null) ? 0 : stuName.hashCode());
		result=prime * result + ((stuNo == null) ? 0 : stuNo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		Student other=(Student)obj;
		if(stuName == null){
			if(other.stuName != null) return false;
		}else if(!stuName.equals(other.stuName)) return false;
		if(stuNo == null){
			if(other.stuNo != null) return false;
		}else if(!stuNo.equals(other.stuNo)) return false;
		return true;
	}
	
}