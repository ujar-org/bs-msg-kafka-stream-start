/*
 * Copyright 2024 IQKV.
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

package com.iqkv.incubator.quickstart.kafkastreaminitialize.config;

import static com.iqkv.incubator.quickstart.kafkastreaminitialize.config.Constants.TOPIC_DEFINITION_FOR_INPUT;
import static com.iqkv.incubator.quickstart.kafkastreaminitialize.config.Constants.TOPIC_DEFINITION_FOR_OUTPUT;

import com.iqkv.boot.kafka.config.KafkaTopicDefinitionProperties;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(value = "iqkv.kafka.admin.create-topics", havingValue = "true")
class KafkaAdminConfig {
  private final KafkaTopicDefinitionProperties topicDefinitions;

  @Bean
  NewTopic inputKafkaTopic() {
    final var definition = topicDefinitions.get(TOPIC_DEFINITION_FOR_INPUT);
    return TopicBuilder
        .name(definition.getName())
        .partitions(definition.getPartitions())
        .config(TopicConfig.RETENTION_MS_CONFIG, "" + definition.getRetention().toMillis())
        .build();
  }

  @Bean
  NewTopic outputKafkaTopic() {
    final var definition = topicDefinitions.get(TOPIC_DEFINITION_FOR_OUTPUT);
    return TopicBuilder
        .name(definition.getName())
        .partitions(definition.getPartitions())
        .config(TopicConfig.RETENTION_MS_CONFIG, "" + definition.getRetention().toMillis())
        .build();
  }
}
