import sbt._
import Keys._

object Build extends sbt.Build {
  lazy val audiowidgets: Project = Project(
    id        = "abc4j-full",
    base      = file("."),
    aggregate = Seq(abc, abcynth),
    settings      = Project.defaultSettings ++ Seq(
      publishArtifact in (Compile, packageBin) := false, // there are no binaries
      publishArtifact in (Compile, packageDoc) := false, // there are no javadocs
      publishArtifact in (Compile, packageSrc) := false  // there are no sources
    )
  )

  lazy val abc = Project(
    id        = "abc4j",
    base      = file("abc"),
    settings  = Project.defaultSettings ++ Seq(
      name        := "abc4j",
      description := "A Java library for music notation",
      libraryDependencies ++= Seq(
        "org.parboiled" % "parboiled-java" % "0.10.0",  // Note: this was 0.9.9. most recent 1.1.5, but anything newer than 0.10.0 fails to compile
        "com.novocode" % "junit-interface" % "0.8" % "test"  // cf. http://www.scala-sbt.org/0.12.3/docs/Detailed-Topics/Testing
      )
    )
  )

  lazy val abcynth = Project(
    id            = "abc4j-abcynth",
    base          = file("abcynth"),
    dependencies  = Seq(abc),
    settings      = Project.defaultSettings ++ Seq(
      name        := "abc4j-abcynth",
      description := "A demo application for abc4j music notation"
    )
  )
}
