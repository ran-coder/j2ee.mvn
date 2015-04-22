package j2ee.research.java.optimize;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 2013-12-25 16:49
 * To change this template use File | Settings | File Templates.
 */
public class SubStringExample{

	public static void main(String[] args){
		String big="bigbigbigbigbigbigbigbigbigbig";
		System.out.println(big.toCharArray()==big.substring(1).toCharArray());
	}
}
