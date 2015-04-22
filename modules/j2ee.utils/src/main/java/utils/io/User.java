package utils.io;

import java.io.Serializable;

public class User implements Serializable{
	private static final long	serialVersionUID	= 4482567484453585165L;
	private String	username;
	private String	password;

	public User() {}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/** toJsonString */
	public String toString(){
		final String TAB = ",";
		StringBuffer retValue = new StringBuffer();
		
		retValue.append("User={")
			.append("\"username\":\"").append(this.username).append("\"").append(TAB)
			.append("\"password\":\"").append(this.password).append("\"").append(TAB)
			;
		retValue.setLength(retValue.length()-1);
		retValue.append("}");
		return retValue.toString();
	}

}
