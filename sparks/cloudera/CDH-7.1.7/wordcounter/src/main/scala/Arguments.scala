import org.rogach.scallop._

class Arguments(arguments: Array[String]) extends ScallopConf(arguments) {
  val interval = opt[Int](default = Some(1))
  val master = opt[String](default = Some("local"))
  val kafka = opt[String](default = Some("localhost:9092"))
  val fromTopic = opt[String](default = Some("from-topic"))
  val toTopic = opt[String](default = Some("to-topic"))
  verify()
}
