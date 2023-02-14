import org.rogach.scallop._

class Arguments(arguments: Array[String]) extends ScallopConf(arguments) {
  val interval = opt[String](default = Some("5 second"))

  val master = opt[String](default = Some("local"))

  val kafka = opt[String](default = Some("localhost:9092"))
  val fromTopic = opt[String](default = Some("from-topic"))
  val toTopic = opt[String](default = Some("to-topic"))

  val dburl = opt[String](default = Some("localhost:1521/XEPDB1"))
  val dbuser = opt[String](default = Some("hr"))
  val dbpassword = opt[String](default = Some("hrpw"))
  val query = opt[String](default = Some("SELECT * FROM EMPLOYEES WHERE SALARY < 6000.00"))
//  val query = opt[String](default = Some("SELECT TABLESPACE_NAME FROM USER_TABLESPACES"))
  verify()
}
