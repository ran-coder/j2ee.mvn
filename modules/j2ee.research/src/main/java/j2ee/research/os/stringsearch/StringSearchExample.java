package j2ee.research.os.stringsearch;

import com.eaio.stringsearch.BNDMWildcards;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 2013-11-15 16:35
 * To change this template use File | Settings | File Templates.
 */
public class StringSearchExample{
	public void testStringSearch() {
		BNDMWildcards bndm = new BNDMWildcards();
		Object compiled = bndm.processString("bla.blorb");
		// "bla?blorb" for StringSearch 1.2
		assertEquals(3,
				bndm.searchString("la bla0blorb null", "bla.blorb", compiled));
	}
	public static void main(String[] args){

	}
}
