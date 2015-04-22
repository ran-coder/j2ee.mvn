package j2ee.tutorial.apache.util.beanutils;

public class Teacher {
	private int id;
	private String username;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * toString
	 */
	public String toString(){
	    final String TAB = "    ";
	    
	    StringBuffer retValue = new StringBuffer();
	    
	    retValue.append("Teacher[")
	        .append("id=").append(this.id).append(TAB)
	        .append("username=").append(this.username).append(TAB)
	        .append("]");
	    
	    return retValue.toString();
	}
	
	
}
