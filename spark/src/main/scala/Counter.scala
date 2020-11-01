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
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setMaster("local").setAppName("NumberCount")
    val streamingContext = new StreamingContext(conf, Seconds(3))

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "kafka:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "use_a_separate_group_id_for_each_stream",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    val topics = Array("topic-from-node")

    val stream = KafkaUtils.createDirectStream[String, String](
      streamingContext,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams)
    )

    val kafkaProducerProps: Properties = {
      val props = new Properties()
      props.put("bootstrap.servers", "kafka:9092")
      props.put("key.serializer", classOf[StringSerializer].getName)
      props.put("value.serializer", classOf[StringSerializer].getName)
      props
    }

    val producer = new KafkaProducer[String, String](kafkaProducerProps)

    val result = stream.map(record => (record.value().toString)).flatMap(_.split(" ")).map(word => (word, 1)).reduceByKey(_ + _)
    result.foreachRDD(rdd => {
      if (rdd.count() > 0) {
        val output = rdd.collect().mkString("[", ", ", "]")
        producer.send(new ProducerRecord[String, String]("topic-to-node", "message", output))
      }
    })

    streamingContext.start()
    streamingContext.awaitTermination()
  }
}
