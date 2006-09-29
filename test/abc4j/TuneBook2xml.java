package test.abc4j;

import java.io.*;
import abc.parser.*;
import abc.notation.*;
import scanner.*;

public class TuneBook2xml
{
  public void generateXMLfrom(File abcFile)
  {
    AbcFileParser parser = new AbcFileParser();
    parser.addListener(new MyParserListener());
    try
    {
      parser.parseFileHeaders(abcFile);
    }
  catch (Exception e)
  {e.printStackTrace();
  }
  }

  private class MyParserListener implements AbcFileParserListenerInterface
  {
    public void tuneBegin()
  {}

  /** Invoked when an invalid token has been parsed.
   * @param evt An event describing the invalid token. */
  public void invalidToken(InvalidTokenEvent evt)
  {}

  /** Invoked when a valid token has been parsed.
   * @param evt An event describing the valid token. */
  public void validToken(TokenEvent evt)
  {}

  /** Invoked when an invalid character has been parsed.
   * @param evt An event describing the invalid character. */
  public void invalidCharacter(InvalidCharacterEvent evt)
  {}

  /** Invoked when the parser reaches the end of a tune.
   * @param tune The tune that has just been parsed. */
  public void tuneEnd(Tune tune)
  {}

  public void fileBegin()
  {

  }

  /** Invoked when a line has been processed.
   * @param line The line that has just been processed. */
  public void lineProcessed(String line)
  {

  }

  /** Invoked when the parsing of the file end. */
  public void fileEnd()
  {

  }


  }
}
