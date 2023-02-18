package org.ujar.bs.msg.kafka.stream.start;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.processor.internals.InternalTopologyBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;

@ExtendWith(MockitoExtension.class)
class KafkaManagedBeanTest {

  @Mock
  StreamsBuilderFactoryBean streamsBuilderFactoryBean;

  @Mock
  Topology topology;

  @Test
  void getTopologyDescription() {

    var underTest = new KafkaManagedBean(streamsBuilderFactoryBean);
    Mockito.when(streamsBuilderFactoryBean.getTopology()).thenReturn(topology);
    Mockito.when(topology.describe()).thenReturn(new InternalTopologyBuilder.TopologyDescription());

    var actual = underTest.getTopologyDescription();

    assertEquals("Topologies:\n"
                 + " ", actual);

  }
}
