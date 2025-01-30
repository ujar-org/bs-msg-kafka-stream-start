/*
 * Copyright 2025 IQKV Team.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.iqkv.incubator.quickstart.kafkastreaminitialize;

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
    topics = {"${iqkv.kafka.topics.for-input.name}",
        "${iqkv.kafka.topics.for-output.name}"})
@EnableKafkaStreams
@DirtiesContext
class InputToOutputStreamTest {

  @Autowired
  EmbeddedKafkaBroker embeddedKafkaBroker;
  @Value("${iqkv.kafka.topics.for-input.name}")
  private String inputTopic;
  @Value("${iqkv.kafka.topics.for-output.name}")
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
