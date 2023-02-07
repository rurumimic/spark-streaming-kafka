name := "spark"

version := "0.1"

scalaVersion := "2.12.17"

resolvers += "Cloudera" at "https://repository.cloudera.com/artifactory/cloudera-repos/"

libraryDependencies += "org.apache.spark" %% "spark-streaming" % "3.2.0.3.2.7170.0-49" % "provided"
libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-10" % "3.2.0.3.2.7170.0-49"

test in assembly := {}
mainClass in assembly := Some("Counter")
assemblyJarName in assembly := "wordcounter.jar"

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
