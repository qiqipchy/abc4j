version in ThisBuild := "0.6.0-SNAPSHOT"

organization in ThisBuild := "de.sciss"

scalaVersion in ThisBuild := "2.10.2"  // not used

homepage in ThisBuild := Some(url("https://github.com/Sciss/abc4j"))

licenses in ThisBuild := Seq("LGPL v3+" -> url("http://www.gnu.org/licenses/lgpl-3.0.txt"))

crossPaths in ThisBuild := false  // this is just a Java project right now!

retrieveManaged in ThisBuild := true

autoScalaLibrary in ThisBuild := false  // currently Java only project

// ---- publishing ----

publishMavenStyle in ThisBuild := true

publishTo in ThisBuild <<= version { v =>
  Some(if (v endsWith "-SNAPSHOT")
    "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
  else
    "Sonatype Releases"  at "https://oss.sonatype.org/service/local/staging/deploy/maven2"
  )
}

publishArtifact in Test := false

pomIncludeRepository in ThisBuild := { _ => false }

pomExtra in ThisBuild := { val n = "abc4j"
<scm>
  <url>git@github.com:Sciss/{n}.git</url>
  <connection>scm:git:git@github.com:Sciss/{n}.git</connection>
</scm>
<developers>
  <developer>
    <id>lionel.gueganton</id>
    <name>Lionel Guéganton</name>
    <url>http://fr.linkedin.com/in/lionelgueganton/fr</url>
  </developer>
  <developer>
    <id>pipemakertjm</id>
    <name>pipemakertjm</name>
    <!-- <url>???</url> -->
  </developer>
  <developer>
    <id>iubito</id>
    <name>Sylvain Machefert</name>
    <url>http://www.tousauxbalkans.net</url>
  </developer>
  <developer>
    <id>sciss</id>
    <name>Hanns Holger Rutz</name>
    <url>http://www.sciss.de</url>
  </developer>
</developers>
}
