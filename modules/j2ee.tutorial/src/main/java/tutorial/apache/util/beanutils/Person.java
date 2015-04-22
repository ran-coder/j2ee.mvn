package tutorial.apache.util.beanutils;

public class Person {
	private String	name;
	private String	favoriteColor;
	private Integer age;
	private String occupation;

	public Person() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name=name;
	}

	public String getFavoriteColor() {
		return favoriteColor;
	}

	public void setFavoriteColor(String favoriteColor) {
		this.favoriteColor=favoriteColor;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age=age;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation=occupation;
	}
	
	
}