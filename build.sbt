name := """Parllet2"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "org.webjars" % "jquery" % "2.1.1",
  "org.webjars" % "bootstrap" % "3.2.0",
//  "org.webjars" %% "webjars-play" % "2.3.0",
//  "org.webjars" % "bootswatch-simplex" % "3.2.0",
  "org.webjars" % "bootswatch" % "3.2.0",
  //  "mysql" % "mysql-connector-java" % "5.1.24",
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4"
)

includeFilter in (Assets, LessKeys.less) := "*.less"

excludeFilter in (Assets, LessKeys.less) := "_*.less"

pipelineStages := Seq(rjs)
