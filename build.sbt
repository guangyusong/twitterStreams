scalaVersion := "2.13.8"

name := "aws-scala"
organization := "twitterStreams"
version := "1.0"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.12" % "test",
  "org.apache.spark" %% "spark-sql" % "3.2.1",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "com.github.pureconfig" %% "pureconfig" % "0.17.1",
  "com.amazonaws" % "aws-java-sdk" % "1.12.215",
  "com.alexdupre" %% "twitter" % "0.1.1"
)
