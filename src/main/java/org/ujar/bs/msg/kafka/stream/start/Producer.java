package org.ujar.bs.msg.kafka.stream.start;

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
                  @Value("${ujar.kafka.topics.for-input.name}") String inputTopic) {
    this.template = template;
    this.inputTopic = inputTopic;
  }

  @Scheduled(fixedRate = 5000)
  public void runner() {
    template.send(inputTopic, "key-" + counter.get(), "value-" + counter.get());
    counter.incrementAndGet();
  }
}
