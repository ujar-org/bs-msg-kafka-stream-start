package org.ujar.bs.msg.kafka.stream.start;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InputToOutputStream {

  private final String kafkaInputTopic;
  private final String kafkaOutputTopic;

  public InputToOutputStream(@Value("${ujar.kafka.topics.for-input.name}") String kafkaInputTopic,
                             @Value("${ujar.kafka.topics.for-output.name}") String kafkaOutputTopic) {
    this.kafkaInputTopic = kafkaInputTopic;
    this.kafkaOutputTopic = kafkaOutputTopic;
  }

  /**
   * Creates a kstream which reads input and the outputs.
   *
   * @param streamsBuilder autowired streams builder
   * @return A kstream of the input
   */
  @Autowired
  public KStream<String, String> buildKStream(StreamsBuilder streamsBuilder) {
    var stream = streamsBuilder.stream(kafkaInputTopic,
        Consumed.with(Serdes.String(), Serdes.String()).withName("streamInput"));

    stream
        .peek((k, v) -> log.info("streams key: {}, value;{} ", k, v))
        .to(kafkaOutputTopic, Produced.with(Serdes.String(), Serdes.String()).withName("streamOutput"));

    return stream;
  }

}
