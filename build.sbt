name := """ExceedVote"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs,
  javaOptions += "-Xmx512m",
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4"
)
