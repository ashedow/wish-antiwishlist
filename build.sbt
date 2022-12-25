name := "scala-akka-app"

version := "0.1"

scalaVersion := "2.13.10"

lazy val akkaVersion = "2.7.0"

// Run in a separate JVM, to make sure sbt waits until all threads have
// finished before returning.
// If you want to keep the application running while executing other
// sbt tasks, consider https://github.com/spray/sbt-revolver/
fork := true

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.11",
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
  "org.scalatest" %% "scalatest" % "3.1.4" % Test,
  "com.lightbend.akka" %% "akka-stream-alpakka-slick" % "5.0.0",
  "mysql" % "mysql-connector-java" % "8.0.15"
)
