package tutorial.lang.math;

/**
 * @(#) HypotMath.java
 * HypotMath class demonstrates the working of hypot() method of 
 * Math class of lang package
 * @version 17-May-2008
 * @author Rose India Team 
 */

public class HypotMath {
	public static void main(String args[]) {

		//Method  returns squareroot of two numbers with form(x*x +y*y)
		//Method returns only double type values
		System.out.println("Square root results");
		System.out.println(Math.hypot(1,1));
		System.out.println(Math.hypot(2L,0));
	}
}