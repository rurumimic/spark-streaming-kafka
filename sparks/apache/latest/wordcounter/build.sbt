name := "spark"

version := "0.1"

scalaVersion := "2.13.10"

libraryDependencies += "org.apache.spark" %% "spark-streaming" % "3.3.1"
libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-10" % "3.3.1"

assembly / test := {}
assembly / mainClass := Some("Counter")
assembly / assemblyJarName := "wordcounter.jar"

assembly / assemblyMergeStrategy := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

//enablePlugins(UniversalPlugin)
//Universal / javaOptions ++= Seq( // commands passed to the JVM
//  "-J--add-exports=java.base/sun.nio.ch=ALL-UNNAMED",
//)
