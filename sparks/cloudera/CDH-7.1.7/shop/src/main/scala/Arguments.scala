import org.rogach.scallop._

class Arguments(arguments: Array[String]) extends ScallopConf(arguments) {
  val interval = opt[Int](default = Some(1))

  val master = opt[String](default = Some("local"))

  val kafka = opt[String](default = Some("localhost:9092"))
  val fromTopic = opt[String](default = Some("topic-from-node"))
  val toTopic = opt[String](default = Some("topic-to-node"))

  val dburl = opt[String](default = Some("localhost:1521/XEPDB1"))
  val dbuser = opt[String](default = Some("hr"))
  val dbpassword = opt[String](default = Some("hrpw"))
  val query = opt[String](default = Some("SELECT * FROM EMPLOYEES WHERE SALARY < 2300"))
//  val query = opt[String](default = Some("SELECT TABLESPACE_NAME FROM USER_TABLESPACES"))
  verify()
}
