package org.ujar.bs.msg.kafka.stream.start;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Component;

@ManagedResource(objectName = "org.ujar.bs.msg.kafka.streams.start:name=KafkaManagedBean")
@Component
public class KafkaManagedBean {
  private final StreamsBuilderFactoryBean streamsBuilderFactoryBean;

  public KafkaManagedBean(StreamsBuilderFactoryBean streamsBuilderFactoryBean) {
    this.streamsBuilderFactoryBean = streamsBuilderFactoryBean;
  }

  @ManagedAttribute(description = "Get the topology description")
  public String getTopologyDescription() {
    return streamsBuilderFactoryBean.getTopology().describe().toString();
  }
}
