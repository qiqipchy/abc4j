# abc4j (fork)

## statement

This is a fork of the [abc4j](https://code.google.com/p/abc4j/) library. The code was originally written by Lionel Guéganton (2006–2008), continued by pipemakertjm (2009) and Sylvain Machefert (iubito) (2009–2011). This library is published under the [GNU Lesser General Public License (LGPL) v3+](https://raw.github.com/Sciss/abc4j/master/LICENSE). All modifications (C)opyright 2013 by Hanns Holger Rutz, released by that same license. Modified source files are marked in the header. The kind of modifications, file removals etc., are documented by way of the git history.

Why a fork? (a) I needed to get an easy handle on the source. (b) I am planning to strip this down to a plain score display component. (c) Github instead of Google-Code. (d) Building with sbt. (e) Publishing (eventually) to Maven Central, making it available for automatic dependency management.

To contact Hanns Holger Rutz, send a mail to `contact@sciss.de`.

## building etc.

To build the project use sbt 0.12. E.g. `sbt test:compile`. To test `sbt test`. To run the demo `sbt run`.

There are currently 8 tests failing in these classes:

    [error] Failed tests:
    [error] 	TuneBookTest
    [error] 	SlursTest
    [error] 	TieTest
    [error] 	GracingsTest

This still has to be investigated...

Also the rendered output from `Main` shows a problem (perhaps with the font): the upward note stems seem to be painted a few pixels too far towards the right.

For `abcynth.PlayerApp` you may want to open the file `src/main/resources/abcynth/LGtunes.abc`.

## overview

Please refer to the [original website](https://code.google.com/p/abc4j/) for documentation.
