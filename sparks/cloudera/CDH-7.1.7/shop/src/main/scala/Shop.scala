import java.util.Properties
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark._
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe

object Shop {
  def main(args: Array[String]): Unit = {

    val arguments = new Arguments(args)
    val INTERVAL = Seconds(arguments.interval())
    val MASTER = arguments.master()
    val KAFKA = arguments.kafka()
    val FROM_TOPIC = arguments.fromTopic()
    val TO_TOPIC = arguments.toTopic()
    val DBURL = arguments.dburl()
    val DBUSER = arguments.dbuser()
    val DBPASSWORD = arguments.dbpassword()
    val QUERY = arguments.query()

    val conf = new SparkConf()
      .setMaster(MASTER)
      .setAppName("Shop")
      .set("spark.oracle.datasource.enabled", "true")
    val spark = SparkSession.builder().config(conf).getOrCreate()
    import spark.implicits._

    val tableDF = spark.read
      .format("jdbc")
      .option("url", s"jdbc:oracle:thin:@$DBURL")
      .option("user", DBUSER)
      .option("password", DBPASSWORD)
      .option("query", QUERY)
      .load()

    val df = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", KAFKA)
      .option("subscribe", FROM_TOPIC)
      .load()
      .selectExpr("CAST(key AS STRING)", "CAST(value AS STRING) as FIRST_NAME")
      .as[(String, String)]
      .join(tableDF, "FIRST_NAME")
      .selectExpr("CAST(FIRST_NAME AS STRING) as key", "CAST(LAST_NAME AS STRING) as value")
    val ds = df
      .selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
      .writeStream
      .format("kafka")
      .option("kafka.bootstrap.servers", KAFKA)
      .option("topic", TO_TOPIC)
      .option("checkpointLocation", "checkpoint")
      .start()

//    val tableDS = tableDF.selectExpr("FIRST_NAME as key", "LAST_NAME as value").write
//      .format("kafka")
//      .option("kafka.bootstrap.servers", KAFKA)
//      .option("topic", TO_TOPIC)
//      .option("checkpointLocation", "checkpoint")
//      .save()

    ds.awaitTermination()
  }
}
