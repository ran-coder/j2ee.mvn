package tutorial.apache.util.beanutils;


public class Book {
	private String name;
	private Person author;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name=name;
	}
	public Person getAuthor() {
		return author;
	}
	public void setAuthor(Person author) {
		this.author=author;
	}
	/** toJsonString */
	public String toString(){
		StringBuilder retValue = new StringBuilder();
	
		retValue.append("{")
			.append("\"name\" : \"").append(this.name).append("\",")
			.append("\"author\" : \"").append(this.author).append("\",")
			;
		if(retValue.length()>0)retValue.setLength(retValue.length()-1);
		return retValue.append("}").toString();
	}
	
}
