import AssemblyKeys._

lazy val commonSettings = Project.defaultSettings ++ Seq(
  version           := "0.6.1-SNAPSHOT",
  organization      := "de.sciss",
  scalaVersion      := "2.10.3",  // not used
  homepage          := Some(url("https://github.com/Sciss/abc4j")),
  licenses          := Seq("LGPL v3+" -> url("http://www.gnu.org/licenses/lgpl-3.0.txt")),
  crossPaths        := false,   // this is just a Java project right now!
  retrieveManaged   := true,
  autoScalaLibrary  := false    // currently Java only project
)

// ---- sub projects ----

lazy val full = Project(
  id            = "abc4j-full",
  base          = file("."),
  aggregate     = Seq(abc, abcynth),
  dependencies  = Seq(abc, abcynth),
  settings      = commonSettings ++ assemblySettings ++ Seq(
    publishArtifact in (Compile, packageBin) := false, // there are no binaries
    publishArtifact in (Compile, packageDoc) := false, // there are no javadocs
    publishArtifact in (Compile, packageSrc) := false, // there are no sources
    test in assembly := (),  // because some currently fail
    jarName in assembly := name.value + ".jar"
  )
)

lazy val abc = Project(
  id        = "abc4j",
  base      = file("abc"),
  settings  = commonSettings ++ Seq(
    name        := "abc4j",
    description := "A Java library for music notation",
    libraryDependencies ++= Seq(
      "org.parboiled" % "parboiled-java" % "0.10.0",  // Note: this was 0.9.9. most recent 1.1.5, but anything newer than 0.10.0 fails to compile
      "com.novocode" % "junit-interface" % "0.8" % "test"  // cf. http://www.scala-sbt.org/0.12.3/docs/Detailed-Topics/Testing
    ),
    javacOptions in Compile ++= Seq("-g", "-target", "1.6" /* , "-Xlint:unchecked" */),  // this is passed to javadoc (WTF?!), so the following line is needed:
    javacOptions in (Compile, doc) := Nil   // yeah right, sssssuckers
  )
)

lazy val abcynth = Project(
  id            = "abc4j-abcynth",
  base          = file("abcynth"),
  dependencies  = Seq(abc),
  settings      = commonSettings ++ Seq(
    name        := "abc4j-abcynth",
    description := "A demo application for abc4j music notation"
  )
)

// ---- publishing ----

publishMavenStyle in ThisBuild := true

publishTo in ThisBuild :=
  Some(if (version.value endsWith "-SNAPSHOT")
    "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
  else
    "Sonatype Releases"  at "https://oss.sonatype.org/service/local/staging/deploy/maven2"
  )

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
