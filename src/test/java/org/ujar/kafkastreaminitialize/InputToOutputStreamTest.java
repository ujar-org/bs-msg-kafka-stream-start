package org.ujar.kafkastreaminitialize;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@EmbeddedKafka(
    partitions = 1,
    controlledShutdown = true,
    ports = 9092,
    brokerProperties = {
        "listeners=PLAINTEXT://localhost:9092",
        "port=9092",
        "log.dir=target/embbdedkafka/InputToOutputStreamTest"
    },
    topics = {"${ujar.kafka.topics.for-input.name}",
        "${ujar.kafka.topics.for-output.name}"})
@EnableKafkaStreams
@DirtiesContext
class InputToOutputStreamTest {

  @Autowired
  EmbeddedKafkaBroker embeddedKafkaBroker;
  @Value("${ujar.kafka.topics.for-input.name}")
  private String inputTopic;
  @Value("${ujar.kafka.topics.for-output.name}")
  private String outputTopic;

  @Test
  public void testReceivingKafkaEvents() {
    Consumer<String, String> consumer = configureConsumer();
    Producer<String, String> producer = configureProducer();

    producer.send(new ProducerRecord<>(inputTopic, "123", "123"));

    ConsumerRecord<String, String> singleRecord = KafkaTestUtils.getSingleRecord(consumer, outputTopic);
    assertThat(singleRecord).isNotNull();
    assertThat(
        singleRecord.value()
    ).isEqualTo("123");
    consumer.close();
    producer.close();
  }

  private Consumer<String, String> configureConsumer() {
    Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("test-group", "true", embeddedKafkaBroker);
    consumerProps.put("key.deserializer", StringDeserializer.class);
    consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    Consumer<String, String> consumer = new DefaultKafkaConsumerFactory<String, String>(consumerProps)
        .createConsumer();
    consumer.subscribe(Collections.singleton(outputTopic));
    return consumer;
  }

  private Producer<String, String> configureProducer() {
    Map<String, Object> producerProps = new HashMap<>(KafkaTestUtils.producerProps(embeddedKafkaBroker));
    producerProps.put("key.serializer", StringSerializer.class);
    return new DefaultKafkaProducerFactory<String, String>(producerProps).createProducer();
  }
}
