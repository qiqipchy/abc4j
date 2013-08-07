// modified by HHR 07-Aug-13

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import junit.framework.TestCase;
import abc.notation.BarLine;
import abc.notation.Tune;
import abc.notation.Note;
//import abc.parser2.PositionableNote;
import abc.notation.TuneBook;
import abc.parser.TuneBookParser;

public class TuneBookTest extends TestCase {
	
	public TuneBookTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}
	
    private static final String RESOURCE_NAME = "testPlan.abc";

    public Reader getResource() {
        try {
            return new InputStreamReader(getClass().getResourceAsStream(RESOURCE_NAME), "UTF-8");
        } catch(UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

	public void test1(){
		// File f = new File("../ressources/testPlan.abc");
		//AbcHeadersParser hp = new AbcHeadersParser();
		try {
			/*hp.addListener(new AbcFileParserAdapter(){
				
				public void lineProcessed(String line) {
					System.out.println(line);
				}
			});*/
			//hp.parseFile(f);
			TuneBook tb = new TuneBookParser().parse(getResource());
			Tune t = tb.getTune(0);
			//Checks the empty score that contains only the time signature and the key
			assertEquals(2, t.getMusic().getFirstVoice().size());
			//Checks if in-file tune header are properly parsed.
			assertNotNull(tb.getTune(0));

			//Checks the 2nd tune : simple scale exercise.
			t = tb.getTune(1);
			//Checks if in-file tune header are properly parsed.
			assertNotNull(tb.getTune(1));
			Note n = null;
			System.out.println(t.getMusic());
			n = (Note)t.getMusic().getFirstVoice().elementAt(2);
			assertEquals(Note.C, n.getStrictHeight());
			assertEquals(1, n.getCharStreamPosition().getColumn());
			assertEquals(5, n.getCharStreamPosition().getLine());
			assertEquals(2, n.getCharStreamPosition().getLength());
			
			n = (Note)t.getMusic().getFirstVoice().elementAt(3);
			assertEquals(Note.D, n.getStrictHeight());
			assertEquals(3, n.getCharStreamPosition().getColumn());
			assertEquals(5, n.getCharStreamPosition().getLine());
			assertEquals(2, n.getCharStreamPosition().getLength());
			
			n = (Note)t.getMusic().getFirstVoice().elementAt(4);
			assertEquals(Note.E, n.getStrictHeight());
			assertEquals(5, n.getCharStreamPosition().getColumn());
			assertEquals(5, n.getCharStreamPosition().getLine());
			assertEquals(2, n.getCharStreamPosition().getLength());
			
			n = (Note)t.getMusic().getFirstVoice().elementAt(5);
			assertEquals(Note.F, n.getStrictHeight());
			assertEquals(7, n.getCharStreamPosition().getColumn());
			assertEquals(5, n.getCharStreamPosition().getLine());
			assertEquals(2, n.getCharStreamPosition().getLength());
			
			assertEquals(BarLine.SIMPLE, ((BarLine)t.getMusic().getFirstVoice().elementAt(6)).getType());
			
			
			n = (Note)t.getMusic().getFirstVoice().elementAt(7);
			assertEquals(Note.G, n.getStrictHeight());
			assertEquals(1, n.getCharStreamPosition().getColumn());
			assertEquals(6, n.getCharStreamPosition().getLine());
			assertEquals(2, n.getCharStreamPosition().getLength());
			
			n = (Note)t.getMusic().getFirstVoice().elementAt(8);
			assertEquals(Note.A, n.getStrictHeight());
			assertEquals(3, n.getCharStreamPosition().getColumn());
			assertEquals(6, n.getCharStreamPosition().getLine());
			assertEquals(2, n.getCharStreamPosition().getLength());
			
			n = (Note)t.getMusic().getFirstVoice().elementAt(9);
			assertEquals(Note.B, n.getStrictHeight());
			assertEquals(5, n.getCharStreamPosition().getColumn());
			assertEquals(6, n.getCharStreamPosition().getLine());
			assertEquals(2, n.getCharStreamPosition().getLength());
			
			n = (Note)t.getMusic().getFirstVoice().elementAt(10);
			assertEquals(Note.C, n.getStrictHeight());
			assertEquals(7, n.getCharStreamPosition().getColumn());
			assertEquals(6, n.getCharStreamPosition().getLine());
			assertEquals(1, n.getCharStreamPosition().getLength());
			
			
			assertEquals(BarLine.SIMPLE, ((BarLine)t.getMusic().getFirstVoice().elementAt(11)).getType());
			
			
		}
		catch (Exception e) {
			//e.printStackTrace();
		}
		
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}


}
