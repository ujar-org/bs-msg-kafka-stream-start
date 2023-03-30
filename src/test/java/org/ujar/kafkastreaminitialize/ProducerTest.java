package org.ujar.kafkastreaminitialize;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

@ExtendWith(MockitoExtension.class)
class ProducerTest {

  @Mock
  KafkaTemplate<String, String> template;


  String inputTopic = "test-topic";

  @Test
  void testRunner() {
    var underTest = new Producer(template, inputTopic);
    underTest.runner();
    underTest.runner();

    Mockito.verify(template, Mockito.times(1)).send(inputTopic, "key-0", "value-0");
    Mockito.verify(template, Mockito.times(1)).send(inputTopic, "key-1", "value-1");

  }
}
