package test.abc4j;

import abc.parser.TuneBook;
import junit.framework.TestCase;

/**
 *
 */
public class BasicTester extends TestCase {
			
	public BasicTester() {
		super("");
	}
	
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	//protected void tearDown() throws Exception {
	//}
	
	public void test_createTuneBook(){
		TuneBook tb = new TuneBook();
		assertTrue(false);
	}
	
	public void test_secondTest(){
		TuneBook tb = new TuneBook();
		assertTrue(true);
	}
	
}
