name := "abc4j"

version := "0.6.0-SNAPSHOT"

organization := "de.sciss"

scalaVersion := "2.10.2"  // not used

description := "A Java library for music notation"

homepage <<= name { n => Some(url("https://github.com/Sciss/" + n)) }

licenses := Seq("LGPL v3+" -> url("http://www.gnu.org/licenses/lgpl-3.0.txt"))

crossPaths := false  // this is just a Java project right now!

retrieveManaged := true

// autoScalaLibrary := false  // currently Java only project

libraryDependencies ++= Seq(
  "org.parboiled" % "parboiled-java" % "0.10.0",  // Note: this was 0.9.9. most recent 1.1.5, but anything newer than 0.10.0 fails to compile
  "com.novocode" % "junit-interface" % "0.8" % "test"  // cf. http://www.scala-sbt.org/0.12.3/docs/Detailed-Topics/Testing
)

// ---- publishing ----

publishMavenStyle := true

publishTo <<= version { v =>
  Some(if (v endsWith "-SNAPSHOT")
    "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
  else
    "Sonatype Releases"  at "https://oss.sonatype.org/service/local/staging/deploy/maven2"
  )
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra <<= name { n =>
<scm>
  <url>git@github.com:Sciss/{n}.git</url>
  <connection>scm:git:git@github.com:Sciss/{n}.git</connection>
</scm>
<developers>
   <developer>
      <id>sciss</id>
      <name>Hanns Holger Rutz</name>
      <url>http://www.sciss.de</url>
   </developer>
</developers>
}
