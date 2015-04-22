package utils.console;

import java.io.IOException;

public class PasswordApp {
	public static void main(String argv[]) {
		char password[]=null;
		try{
			password=PasswordField.getPassword(System.in,"Enter your password: ");
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
		if(password == null){
			System.out.println("No password entered");
		}else{
			System.out.println("The password entered is: " + String.valueOf(password));
		}
	}
}
