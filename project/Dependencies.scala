import sbt._

object Dependencies {
  val pureconfigVersion = "0.15.0"
  val sparkVersion = "3.2.1"

  lazy val core = Seq(
    // support for typesafe configuration
    "com.github.pureconfig" %% "pureconfig" % pureconfigVersion,
    // spark
    "org.apache.spark" %% "spark-sql" % sparkVersion % Provided,
    // logging
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"
  )
}
