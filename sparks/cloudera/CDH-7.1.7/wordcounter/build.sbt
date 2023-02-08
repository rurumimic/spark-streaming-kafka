name := "spark"

version := "0.1"

scalaVersion := "2.12.17"

resolvers += "Cloudera" at "https://repository.cloudera.com/artifactory/cloudera-repos/"

libraryDependencies += "org.apache.spark" %% "spark-streaming" % "3.2.0.3.2.7170.0-49"
libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-10" % "3.2.0.3.2.7170.0-49"

libraryDependencies += "org.rogach" %% "scallop" % "4.1.0"

assembly / test := {}
assembly / mainClass := Some("Counter")
assembly / assemblyJarName := "wordcounter.jar"

assembly / assemblyMergeStrategy := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}