// modified by HHR 07-Aug-13

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import junit.framework.TestCase;
import abc.notation.Music;
import abc.notation.Note;
import abc.notation.TuneBook;
import abc.parser.TuneBookParser;

public class SlursTest extends TestCase {
	
	private static final String RESOURCE_NAME = "crash.abc";
	
	public SlursTest(String name) {
		super(name);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
	}

    public Reader getResource() {
        try {
            return new InputStreamReader(getClass().getResourceAsStream(RESOURCE_NAME), "UTF-8");
        } catch(UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

	/**
	 * (cd)
	 *
	 */
	public void test1(){
		try {
			TuneBook tb = new TuneBookParser().parse(getResource());
			checkSlursInScore(tb.getTune(26).getMusic());
/*			Note firstNote = (Note)score.elementAt(1);
			Note secondNote = (Note)score.elementAt(2);
			assertTrue(firstNote.isPartOfSlur());
			assertTrue(secondNote.isPartOfSlur());
			assertNotNull(firstNote.getSlurDefinition());
			assertNotNull(secondNote.getSlurDefinition());
			assertEquals(firstNote.getSlurDefinition().getStart(), firstNote);
			assertEquals(firstNote.getSlurDefinition().getEnd(), secondNote);
			assertEquals(firstNote.getSlurDefinition(), secondNote.getSlurDefinition());*/
		}
		catch (Exception e ) {
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * (abcdefg)
	 */
	public void test2(){
		try {
			TuneBook tb = new TuneBookParser().parse(getResource());
			checkSlursInScore(tb.getTune(27).getMusic());
		}
		catch (Exception e ) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * (a|b)
	 */
	public void test3(){
		try {
			TuneBook tb = new TuneBookParser().parse(getResource());
			checkSlursInScore(tb.getTune(29).getMusic());
		}
		catch (Exception e ) {
			throw new RuntimeException(e);
		}
	}
	
	private void checkSlursInScore(Music score) {
		Note firstNote = null;
		Note lastNote = null;
		for (int i=0; i<score.getFirstVoice().size(); i++)
			if (score.getFirstVoice().elementAt(i) instanceof Note) {
				Note note = ((Note)score.getFirstVoice().elementAt(i));
				if (firstNote==null)
					firstNote = note;
				lastNote = note;
				assertTrue(note.isPartOfSlur());
			}
        assertNotNull(firstNote);
        assertNotNull(firstNote.getSlurDefinitions().firstElement());
		assertFalse(firstNote.isTied());
		assertFalse(lastNote.isTied());
		assertTrue(firstNote.isBeginingSlur());
		assertTrue(lastNote.isEndingSlur());
		assertEquals(
				firstNote.getSlurDefinitions().firstElement(),
				lastNote.getSlurDefinitions().firstElement());
		
	}
	
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}


}
