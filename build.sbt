name := "db"

version := "0.1"

scalaVersion := "2.12.10"

libraryDependencies ++= Seq(
  // "org.apache.kafka" %% "kafka" % "2.1.0",
//  "com.typesafe.play" %% "play-json" % "2.8.1",
  "org.scalikejdbc" %% "scalikejdbc" % "3.4.0",
  "com.h2database" % "h2" % "1.4.200",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "mysql" % "mysql-connector-java" % "8.0.19",
  "com.typesafe.play" %% "play-json" % "2.6.14",
  "org.scalactic" %% "scalactic" % "3.1.1",
  "org.scalatest" %% "scalatest" % "3.1.1" % "test"
)
