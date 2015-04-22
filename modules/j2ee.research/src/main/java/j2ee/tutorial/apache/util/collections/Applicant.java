package j2ee.tutorial.apache.util.collections;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Applicant {

	private String	name;
	private int		age;
	private String	applyFor;

	public Applicant() {}

	public Applicant(String name, int age, String applyFor) {
		this.name = name;
		this.age = age;
		this.applyFor = applyFor;
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("name", name).append("age", age)
			.append("applyFor", applyFor).toString();
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getApplyFor() {
		return applyFor;
	}

	public void setApplyFor(String applyFor) {
		this.applyFor = applyFor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}