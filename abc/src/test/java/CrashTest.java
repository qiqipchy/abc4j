// modified by HHR 07-Aug-13

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import junit.framework.TestCase;
import abc.parser.TuneBookParser;

public class CrashTest extends TestCase {
	
	private static final String RESOURCE_NAME = "crash.abc";
	
	public CrashTest(String name) {
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

	public void test1(){
		try {
		// File f = new File(RESOURCE_NAME);
		// System.out.println((new File(".")).getAbsolutePath());
		TuneBookParser hparser = new TuneBookParser();
		//hparser.addListener(new ParsingDumper());
		long headersParsingTime = 0;
			long start = System.currentTimeMillis();
			hparser.parseHeaders(getResource());
			long end = System.currentTimeMillis();
			headersParsingTime = end - start;
			System.out.println("Headers only : " + headersParsingTime); 
		}
		catch (Exception e ) {
			System.out.println("There's an exception here " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	
//	public class ParsingDumper implements AbcFileParserListenerInterface {
//
///*		public void tuneEnd(Tune tune) {
//			System.out.println(tune.getReferenceNumber() + " : " + tune.getTitles()[0]);
//		}
//		public void validToken(TokenEvent event) {
//			System.out.println("tok : " + event);
//		}
//
//		public void invalidCharacter(InvalidCharacterEvent evt) {
//			System.err.println("invalid char : " + evt);
//		}
//
//		public void invalidToken(InvalidTokenEvent evt) {
//			System.err.println("invalid token : " + evt);
//		}
//
//		 public void lineProcessed(String line) {
//			 System.out.println("line processed : " + line);
//		 }*/
//		public void fileBegin() {
//		}
//		public void fileEnd() {
//		}
//		public void noTune() {
//		}
//		public void tuneBegin() {
//		}
//		public void tuneEnd(Tune tune, AbcNode abcRoot) {
//			System.out.println(tune.getReferenceNumber() + " : " + tune.getTuneInfos().get(TuneInfos.TITLE));
//			if (abcRoot.hasError()) {
//				AbcParserAbstract.debugTree(abcRoot);
//			}
//		}
//	}

}
