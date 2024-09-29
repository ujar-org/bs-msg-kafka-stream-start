package dev.knowhowto.kafkastreaminitialize.config;

import static dev.knowhowto.kafkastreaminitialize.config.Constants.TOPIC_DEFINITION_FOR_INPUT;
import static dev.knowhowto.kafkastreaminitialize.config.Constants.TOPIC_DEFINITION_FOR_OUTPUT;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import com.iqkv.boot.kafka.config.KafkaTopicDefinitionProperties;
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
