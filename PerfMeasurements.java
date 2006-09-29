import abc.notation.*;
import abc.parser.*;
import scanner.*;
import java.util.*;
import java.io.*;
import java.awt.*;

/** A simple user interface to display abc files content and play
 * tunes. */
public class PerfMeasurements //implements TunePlayerListenerInterface
{
  public static final int PARSING_REPEAT_NUMBER = 10;
  public static final String REF_FILE_NAME = "C:/MyStuff/partitions/ABC/Oneill~1.abc";
  private static String m_perfFileName = null;
  private static int m_perfRepeatNb = 0;

  public static void main (String[] arg)
  {
    if (arg.length>0)
    	m_perfFileName=arg[0];
    if (arg.length>1)
    	m_perfRepeatNb=new Integer(arg[1]).intValue();
    AbcFileParser parser = new AbcFileParser();
    /*parser.addListener(new AbcFileParserAdapter()
    {
      public void tuneEnd(Tune tune)
      {
        System.out.println("tune parsed : " + tune.getTitles()[0]);
        tunesNb++;
      }

      public void lineProcessed(String line)
      {
        //System.out.print("line processed : " + line);
      }
    });*/
    try
    {
      System.out.println("Parsing " + m_perfFileName + " " + m_perfRepeatNb + " times : ");
      long ref = System.currentTimeMillis();
      long intermediateRef = ref;
      long end = 0;
      System.out.println("===============PARSING TUNES HEADERS ONLY");
      for (int i=0;i<m_perfRepeatNb; i++)
      {
      	parser.parseFileHeaders(new File(m_perfFileName));
      	end = System.currentTimeMillis();
      	System.out.println("Parsing " + i + " took " + (end-intermediateRef) + " millisecs");
      	intermediateRef = end;
      }
      //System.out.println("end : " + end);
      System.out.println("Average parsing time : " + (end-ref)/m_perfRepeatNb + " millisecs");
      System.out.println("===============PARSING WHOLE FILE");
      ref = System.currentTimeMillis();
      intermediateRef = ref;
      end = 0;
      for (int i=0;i<m_perfRepeatNb; i++)
      {
      	parser.parseFile(new File(m_perfFileName));
      	end = System.currentTimeMillis();
      	System.out.println("Parsing " + i + " took " + (end-intermediateRef) + " millisecs");
      	intermediateRef = end;
      }
      //System.out.println("end : " + end);
      System.out.println("Average parsing time : " + (end-ref)/m_perfRepeatNb + " millisecs");
      System.out.println("===============CREATING A TUNE BOOK");
      ref = System.currentTimeMillis();
      intermediateRef = ref;
      end = 0;
      for (int i=0;i<m_perfRepeatNb; i++)
      {
      	TuneBook book = new TuneBook(new File(m_perfFileName));
      	end = System.currentTimeMillis();
      	System.out.println("TuneBook " + i + " took " + (end-intermediateRef) + " millisecs");
      	intermediateRef = end;
      }
      //System.out.println("end : " + end);
      System.out.println("Average parsing time : " + (end-ref)/m_perfRepeatNb + " millisecs");
      
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

}
