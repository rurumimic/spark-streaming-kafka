name := "spark"

version := "0.1"

scalaVersion := "2.13.10"

libraryDependencies += "org.apache.spark" %% "spark-streaming" % "3.3.1"
libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-10" % "3.3.1"

test in assembly := {}
mainClass in assembly := Some("Counter")
assemblyJarName in assembly := "wordcounter.jar"

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
