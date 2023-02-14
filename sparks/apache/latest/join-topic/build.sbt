name := "spark"

version := "0.1"

scalaVersion := "2.13.10"

libraryDependencies += "org.apache.spark" %% "spark-streaming" % "3.3.1"
libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-10" % "3.3.1"

libraryDependencies += "org.rogach" %% "scallop" % "4.1.0"

assembly / test := {}
assembly / mainClass := Some("JoinTopic")
assembly / assemblyJarName := "join-topic.jar"

assembly / assemblyMergeStrategy := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
