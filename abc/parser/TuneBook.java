package abc.parser;
import java.io.*;
import java.util.Vector;
import java.util.TreeMap;
import java.util.Iterator;
import abc.notation.*;
import scanner.*;

/** This class provides an object representation of a tunebook. It enables you
 * to store tunes ordered by reference number. */
public class TuneBook
{
  /** The file parser used to initialize the tunebook. */
  private AbcFileParser m_fileParser = null;
  /** The tune parser used to parse tunes notation. */
  private TuneParser m_parser = null;

  private TreeMap m_tunes = null;
  /** Listeners of this tunebook. */
  private Vector m_listeners = null;
  private Vector m_originalTunesOrder = null;
  private File m_file = null;

  /** Creates a new tune book from the specified file.
   * @param abcFile The file that contains tunes in abc notation.
   * @exception FileNotFoundException Thrown if the specified file doesn't exist. */
  public TuneBook(File abcFile) throws FileNotFoundException
  {
    this(new BufferedReader(new InputStreamReader(new FileInputStream (abcFile))));
    m_file = abcFile;
  }

  public TuneBook(File abcFile, AbcFileParserListenerInterface listener) throws FileNotFoundException
  {
    this(new BufferedReader(new InputStreamReader(new FileInputStream (abcFile))),listener);
    m_file = abcFile;
  }

  /** Creates a new tune book from the specified stream.
   * @param stream The stream in abc notation. */
  public TuneBook(BufferedReader stream)
  {
    this();
    buildTunesTreeMap(stream, null);
  }

  public TuneBook(BufferedReader stream, AbcFileParserListenerInterface listener)
  {
    this();
    buildTunesTreeMap(stream, listener);
  }

  /** Creates an empty tunebook. */
  public TuneBook()
  {
    m_fileParser = new AbcFileParser();
    m_parser = new TuneParser();
    m_tunes = new TreeMap();
    m_originalTunesOrder = new Vector();
    m_listeners = new Vector();
  }

  /** Saves this tunebook to the specified file.
   * @param file The file where all tunes notation should be stored.
   * @exception IOException Thrown if the specified file doesn't exist. */
  public void saveTo(File file) throws IOException
  {
    m_file = file;
    save();
  }

  public File getFile()
  { return m_file; }

  public void save() throws IOException
  {
    FileWriter writer = new FileWriter(m_file);
    for (int i=0; i<m_originalTunesOrder.size(); i++)
    {
      TranscribedTune tune = (TranscribedTune)m_originalTunesOrder.elementAt(i);
      if (tune.header!=null) writer.write(tune.header);
      writer.write(tune.notation);
      if (
          tune.notation.charAt(tune.notation.length()-1)!='\n'
          || tune.notation.charAt(tune.notation.length()-2)!='\n'
          )
        writer.write("\n");
    }
    writer.flush();
    System.out.println("Saving to " + m_file.toString());
  }

  /** Puts the specified notation in this tunebook.
   * If a tune with the same reference number was already existing, it updates
   * it. If it's a new tune, it adds it.
   * @param tuneNotation A string that describes a tune using ABC notation.
   * @return Header information of the added tune. */
  public Tune putTune(String tuneNotation)
  {
    Tune parsedTune = m_parser.parseHeader(tuneNotation);
    Integer key = new Integer(parsedTune.getReferenceNumber());
    TranscribedTune tune = (TranscribedTune)m_tunes.get(key);
    if (tune!=null)
    {
      tune.notation = tuneNotation;
      tune.tune = parsedTune;
      tune.m_isHeader = true;
      notifyListenersForTuneChange(new TuneChangeEvent(this, TuneChangeEvent.TUNE_UPDATED, tune.tune, tuneNotation));
    }
    else
    {
      tune = new TranscribedTune();
      tune.notation = tuneNotation;
      tune.tune = parsedTune;
      tune.m_isHeader = true;
      tune.header = "";
      m_tunes.put(key, tune);
      m_originalTunesOrder.addElement(tune);
      notifyListenersForTuneChange(new TuneChangeEvent(this, TuneChangeEvent.TUNE_ADDED, tune.tune, tuneNotation));
    }
    return parsedTune;
  }

  /** Removes the tune with specified reference number.
   * @param referenceNumber The reference number of the tune that has to be
   * removed.
   * @return The tune that has been removed, <TT>null</TT> if no tune with the
   * corresponding reference number has been found. */
  public Tune removeTune(int referenceNumber)
  {
    if (m_tunes.remove(new Integer(referenceNumber))!=null)
    {
      TranscribedTune tune= null;
      for (int i=0; i<m_originalTunesOrder.size(); i++)
      {
        tune = (TranscribedTune)m_originalTunesOrder.elementAt(i);
        if (tune.tune.getReferenceNumber()==referenceNumber)
        {
          m_originalTunesOrder.removeElementAt(i);
          notifyListenersForTuneChange(new TuneChangeEvent(this, TuneChangeEvent.TUNE_REMOVED, tune.tune, tune.notation));
          return tune.tune;
        }
      }
    }
    return null;
  }

  /** Returns the tune with the specified reference number
   * @param referenceNumber The reference number of the tune that should be retrieved.
   * @return The tune corresponding to the specified reference number, <TT>null</TT>
   * if no tune found. */
  public Tune getTune(int referenceNumber)
  {
    Integer key = new Integer(referenceNumber);
    TranscribedTune tune = (TranscribedTune)m_tunes.get(key);
    if (tune!=null)
    {
      if (tune.m_isHeader==true)
      {
        tune.tune = m_parser.parse(tune.notation);
        tune.m_isHeader = false;
      }
      return tune.tune;
    }
    return null;
  }

  public String getTuneHeader(int referenceNumber)
  {
    Integer key = new Integer(referenceNumber);
    TranscribedTune tune = (TranscribedTune)m_tunes.get(key);
    if (tune!=null)
    {
      return tune.header;
    }
    return null;
  }

  /** Returns the notation of the tune corresponding to the specified
   * reference number.
   * @param referenceNumber A reference number.
   * @return A tune notation in ABC format. <TT>null</TT> if no tune has been
   * found.
   * @exception NoSuchTuneException Thrown if the specified reference number
   * doesn't exist in this tunebook. */
  public String getTuneNotation(int referenceNumber) throws NoSuchTuneException
  {
    Integer key = new Integer(referenceNumber);
    TranscribedTune tune = (TranscribedTune)m_tunes.get(key);
    if (tune!=null)
      return tune.notation;
    else
      return null;
  }

  /** Returns tunes header information of tunes contained in this tunebook.
   * @return An array containing <TT>Tune</TT> objects representing header
   * information of tunes contained in this tunebook. */
  public Tune[] getTunesHeaders()
  {
    Iterator it = m_tunes.keySet().iterator();
    Tune[] tunes = new Tune[m_tunes.size()];
    int index=0;
    while (it.hasNext())
    {
      tunes[index] = (Tune)m_tunes.get(it.next());
      index++;
    }
    return tunes;
  }

  /** Returns the reference numbers of tunes contained in this tunebook.
   * @return An array containing the reference numbers of tunes contained in
   * this tunebook, ordered in the way they were added in this tunebook. */
  public int[] getReferenceNumbers()
  {
    Iterator it = m_tunes.keySet().iterator();
    int[] refNb = new int[m_tunes.size()];
    int index=0;
    while (it.hasNext())
    {
      refNb[index] = ((Integer)it.next()).intValue();
      index++;
    }
    return refNb;
  }

  public int getHighestReferenceNumber()
  {
    int[] refNumbers = getReferenceNumbers();
    int hi = -1;
    for (int i=0; i<refNumbers.length; i++)
      if (refNumbers[i]>hi)
        hi = refNumbers[i];
    return hi;
  }

  /** Returns the number of tunes contained in this tunebook.
   * @return The number of tunes contained in this tunebook. */
  public int size()
  { return m_tunes.size(); }

  public Vector toVector()
  {
    java.util.Set keys = m_tunes.keySet();
    Vector v = new Vector();
    Iterator keysIterator = keys.iterator();
    while (keysIterator.hasNext())
      v.addElement(((TranscribedTune)m_tunes.get(keysIterator.next())).tune);
    return v;
  }

  /** Adds a listener to this tunebook to be aware of tunes changes.
   * @param l The listener to be added. */
  public void addListener(TuneBookListenerInterface l)
  { m_listeners.addElement(l); }

  /** Removes a listener from this tunebook.
   * @param l The listener to be removed. */
  public void removeListener(TuneBookListenerInterface l)
  { m_listeners.removeElement(l); }

  protected void notifyListenersForTuneChange(TuneChangeEvent e)
  {
    for (int i=0; i<m_listeners.size() ;i++)
    ((TuneBookListenerInterface)m_listeners.elementAt(i)).tuneChanged(e);
  }

  private void buildTunesTreeMap(BufferedReader readerStram, AbcFileParserListenerInterface clientListener)
  {
    m_tunes = new TreeMap();
    ParserListener listener = new ParserListener();
    m_fileParser.addListener(listener);
    if (clientListener!=null)
      m_fileParser.addListener(clientListener);
    m_fileParser.parseFileHeaders(readerStram);
    m_fileParser.removeListener(listener);
    m_fileParser.removeListener(clientListener);
  }

  private class TranscribedTune
  {
    public String header = null;
    public Tune tune = null;
    public String notation = null;
    public boolean m_isHeader = true;
  }

  private class ParserListener implements ScannerListenerInterface, AbcFileParserListenerInterface
  {
    private StringBuffer m_currentTuneNotation = null;
    private StringBuffer m_currentHeader = null;
    private boolean isInTune = false;

    public void fileBegin()
    {
     // System.out.println("file begin");
     m_currentHeader = new StringBuffer();
     m_currentTuneNotation = new StringBuffer();
    }

    public void tuneBegin()
    {
      //System.out.println("======================tune begin");
      isInTune = true;
    }

    public void invalidToken(InvalidTokenEvent event)
    {}

    public void validToken(TokenEvent event)
    {}

    public void invalidCharacter(InvalidCharacterEvent event)
    {}

    public void tuneEnd(Tune tune)
    {
      //System.out.println("===================tune end");
      TranscribedTune transcribedTune = new TranscribedTune();
      transcribedTune.tune = tune;
      transcribedTune.notation = new String(m_currentTuneNotation);
      if (m_currentHeader.length()>0)transcribedTune.header = new String(m_currentHeader);
      m_currentTuneNotation = new StringBuffer();
      m_currentHeader = new StringBuffer();
      m_tunes.put(new Integer(tune.getReferenceNumber()), transcribedTune);
      m_originalTunesOrder.addElement(transcribedTune);
      notifyListenersForTuneChange(new TuneChangeEvent(this, TuneChangeEvent.TUNE_UPDATED, tune, transcribedTune.notation));
      isInTune = false;
    }

    public void fileEnd()
    {
      //System.out.println("file End");
    }

    public void lineProcessed(String line)
    {
      //System.out.print(this.getClass().getName() + " line :"  + line);
      if (isInTune)
        m_currentTuneNotation.append(line);
      else
        m_currentHeader.append(line);
    }

    public void tokenGenerated(TokenEvent event)
    {}
  }
}

