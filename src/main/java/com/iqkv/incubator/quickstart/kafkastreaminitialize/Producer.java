/*
 * Copyright 2024 IQKV Team.
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

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
@ConditionalOnProperty(prefix = "producer", name = "enabled", havingValue = "true", matchIfMissing = true)
public class Producer {
  private final KafkaTemplate<String, String> template;
  private final String inputTopic;
  private final AtomicInteger counter = new AtomicInteger();

  public Producer(KafkaTemplate<String, String> template,
                  @Value("${iqkv.kafka.topics.for-input.name}") String inputTopic) {
    this.template = template;
    this.inputTopic = inputTopic;
  }

  @Scheduled(fixedRate = 5000)
  public void runner() {
    template.send(inputTopic, "key-" + counter.get(), "value-" + counter.get());
    counter.incrementAndGet();
  }
}
