package abc.examples;// modified by HHR 07-Aug-13

// Copyright 2006-2008 Lionel Gueganton
// This file is part of abc4j.
//
// abc4j is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// abc4j is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with abc4j.  If not, see <http://www.gnu.org/licenses/>.

import java.awt.*;
import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;

import abc.notation.Tune;
import abc.notation.TuneBook;
import abc.parser.TuneBookParser;
import abc.ui.swing.JScoreComponent;

//import jm.music.data.*;
//import jm.gui.show.*;
//import jm.gui.cpn.*;


/** A simple user interface to display abc files content and play
 * tunes.  
 * Main entry point to execute some perf tests or any other 
 * operation. */
public class Main  {
    public static final String DEMO_RESOURCE_NAME       = "LGtunes.abc";
    public static final String CHROMATIC_RECOURCE_NAME  = "Chromatic.abc";

  //static int PARSING_TIMES_NB = 1;
  //static int tunesNb = 0;
  /*public static void main (String[] arg) {
	  boolean endOfStreamReached = false;
	  char[] currentChar = new char[1];
	  long start  = 0;
	  long end = 0;
  	  try {
  		  
  		  
  		  File f = new File("D:/Perso/abc/LGTunes.abc");
  		  BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream (f)));
  		  
  		  int k=1;
  		  Scanner2 s = new Scanner2(r);
  		  while (k<1000) {
  		  	System.out.print(s.nextChar());
  		  	s.nextChar();
  		  	s.previousChar();
  		  }
  		  
  		  System.out.println(r);
  		  start = System.currentTimeMillis();
  		  while (!endOfStreamReached) {
  			  r.mark(1);
  			  if (r.read(currentChar)==-1)
  				  endOfStreamReached = true;
  		  }
  		  end = System.currentTimeMillis();
  		  r.close();
  		  System.out.println("Done in " + (end-start));
  		  
  		  endOfStreamReached = false;
		  r = new BufferedReader(new InputStreamReader(new FileInputStream (f)));
		  System.out.println(r);
		  String line ="";
		  start = System.currentTimeMillis();
		  while (!endOfStreamReached) {
			  line = r.readLine();
			  if (line==null)
				  endOfStreamReached = true;
			  else {
				  int i = 0;
				  while (i<line.length()) {
					  char c = line.charAt(i);
					  i++;
				  }
			  }
		  }
		  end = System.currentTimeMillis();
		  r.close();
		  System.out.println("Done in " + (end-start));
		  
		  endOfStreamReached = false;
		  r = new BufferedReader(new FileReader(f));
		  System.out.println(r);
  		  start = System.currentTimeMillis();
  		  while (!endOfStreamReached) {
  			  r.mark(1);
  			  if (r.read(currentChar)==-1)
  				  endOfStreamReached = true;
  		  }
  		  end = System.currentTimeMillis();
  		  r.close();
  		  System.out.println("Done in " + (end-start));
  		  
  		  endOfStreamReached = false;
		  r = new BufferedReader(new FileReader(f));
		  System.out.println(r);
		  line ="";
		  start = System.currentTimeMillis();
		  while (!endOfStreamReached) {
			  line = r.readLine();
			  if (line==null)
				  endOfStreamReached = true;
		  }
		  end = System.currentTimeMillis();
		  r.close();
		  System.out.println("Done in " + (end-start));
  		  
  		  
  	  }	catch (Exception e) {
  		  e.printStackTrace();
  	  }
  }*/
	
	/*public static void main (String[] arg) {
		String tuneAsString = "X:0\nT:A simple scale exercise\nM:4/4\nK:D\n(CD EF|G)A Bc|de fg-|gf ed|cB A(G|FE DC)\n";
		Tune tune = new TuneParser().parse(tuneAsString);
		JScoreComponent scoreUI =new JScoreComponent();
		scoreUI.setTune(tune);
		JFrame j = new JFrame();
		j.add(scoreUI);
		j.pack();
		//System.out.println(sp.getSize());
		j.setVisible(true);
		}
	}*/
	
	
	public static void main (String[] arg) {
		try {
            boolean chr = arg.length > 0 && arg[0].equals("--chromatic");
            String rn   = chr ? CHROMATIC_RECOURCE_NAME : DEMO_RESOURCE_NAME;
            Reader isr  = new InputStreamReader(Main.class.getResourceAsStream(rn), "UTF-8");
            TuneBook tb = new TuneBookParser().parse(isr);
		    Tune tune   = tb.getTune(chr ? 1 : 7);
		    JScoreComponent scoreUI = new JScoreComponent();
		    scoreUI.setTune(tune);
            // Dimension d     = scoreUI.getSize();
            // d.height       *= 3;
            // scoreUI.setSize(d);
            File home       = new File(System.getProperty("user.home"));
            File desktop    = new File(home, "Desktop");
            File dir        = desktop.isDirectory() ? desktop : home;
		    scoreUI.writeScoreTo(new File(dir, "abc4j_demoNotjustified.jpg"));
		    scoreUI.setJustification(true);
		    scoreUI.writeScoreTo(new File(dir, "abc4j_demoJustified.jpg"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*public static void main (String[] arg) {
		Tune tune = new Tune();
		KeySignature key = new KeySignature(Note.D, KeySignature.MAJOR);
		tune.getMusic().addElement(key);
		Tune.Music music = tune.getMusic();
		music.addElement(TimeSignature.SIGNATURE_4_4);
		Note noteFirstSlurBegin = new Note(Note.C); 
		music.addElement(noteFirstSlurBegin);
		music.addElement(new Note(Note.D));
		music.addElement(new NotesSeparator());
		music.addElement(new Note(Note.E));
		music.addElement(new Note(Note.F));
		music.addElement(new BarLine());
		Note noteFirstSlurEnd =  new Note(Note.G);
		music.addElement(noteFirstSlurEnd);
		music.addElement(new Note(Note.A));
		music.addElement(new NotesSeparator());
		music.addElement(new Note(Note.B));
		music.addElement(new Note(Note.c));
		music.addElement(new BarLine());
		music.addElement(new Note(Note.d));
		music.addElement(new Note(Note.e));
		music.addElement(new NotesSeparator());
		music.addElement(new Note(Note.f));
		Note noteStartingTie = new Note(Note.g); 
		music.addElement(noteStartingTie);
		music.addElement(new BarLine());
		Note noteEndingTie = new Note(Note.g);
		music.addElement(noteEndingTie);
		music.addElement(new Note(Note.f));
		music.addElement(new NotesSeparator());
		music.addElement(new Note(Note.e));
		music.addElement(new Note(Note.d));
		music.addElement(new BarLine());
		music.addElement(new Note(Note.c));
		music.addElement(new Note(Note.B));
		music.addElement(new NotesSeparator());
		music.addElement(new Note(Note.A));
		Note noteSecondSlurBegin = new Note(Note.G); 
		music.addElement(noteSecondSlurBegin);
		music.addElement(new BarLine());
		music.addElement(new Note(Note.F));
		music.addElement(new Note(Note.E));
		music.addElement(new NotesSeparator());
		music.addElement(new Note(Note.D));
		Note noteSecondSlurEnd = new Note(Note.C);
		music.addElement(noteSecondSlurEnd);
		//first slur definition
		SlurDefinition firstSlur = new SlurDefinition();
		firstSlur.setStart(noteFirstSlurBegin);
		firstSlur.setEnd(noteFirstSlurEnd);
		noteFirstSlurBegin.setSlurDefinition(firstSlur);
		noteFirstSlurEnd.setSlurDefinition(firstSlur);
		//second slur definition
		SlurDefinition secondSlur = new SlurDefinition();
		secondSlur.setStart(noteSecondSlurBegin);
		secondSlur.setEnd(noteSecondSlurEnd);
		noteSecondSlurBegin.setSlurDefinition(secondSlur);
		noteSecondSlurEnd.setSlurDefinition(secondSlur);
		//tie between the two g notes.
		TieDefinition tie = new TieDefinition();
		tie.setStart(noteStartingTie);
		tie.setEnd(noteEndingTie);
		noteStartingTie.setTieDefinition(tie);
		noteEndingTie.setTieDefinition(tie);
		JScoreComponent scoreUI =new JScoreComponent();
		scoreUI.setTune(tune);
		JFrame j = new JFrame();
		j.add(scoreUI);
		j.pack();
		j.setVisible(true);
	}*/
	
}
