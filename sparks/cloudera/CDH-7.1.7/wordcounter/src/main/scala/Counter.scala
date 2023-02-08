import java.util.Properties

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe

object Counter {

  val INTERVAL = Seconds(1);

  val MASTER = "local"
  val KAFKA = "kafka:9092" // "localhost:9092"

  val FROM_TOPIC = "topic-from-node"
  val TO_TOPIC = "topic-to-node"

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setMaster(MASTER).setAppName("NumberCount")
    val streamingContext = new StreamingContext(conf, INTERVAL)

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> KAFKA,
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "use_a_separate_group_id_for_each_stream",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    val topics = Array(FROM_TOPIC)

    val stream = KafkaUtils.createDirectStream[String, String](
      streamingContext,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams)
    )

    val kafkaProducerProps: Properties = {
      val props = new Properties()
      props.put("bootstrap.servers", KAFKA)
      props.put("key.serializer", classOf[StringSerializer].getName)
      props.put("value.serializer", classOf[StringSerializer].getName)
      props
    }

    val producer = new KafkaProducer[String, String](kafkaProducerProps)

    val result = stream.map(record => (record.value().toString)).flatMap(_.split(" ")).map(word => (word, 1)).reduceByKey(_ + _)
    result.foreachRDD(rdd => {
      if (rdd.count() > 0) {
        val output = rdd.sortBy(_._2, false).collect().mkString("[", ", ", "]")
        producer.send(new ProducerRecord[String, String](TO_TOPIC, "message", output))
      }
    })

    streamingContext.start()
    streamingContext.awaitTermination()
  }
}
