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
