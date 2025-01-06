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

package com.iqkv.incubator.quickstart.kafkastreaminitialize;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Component;

@ManagedResource(objectName = "dev.iqkv.kafka.streams.start:name=KafkaManagedBean")
@Component
public class KafkaManagedBean {
  private final StreamsBuilderFactoryBean streamsBuilderFactoryBean;

  public KafkaManagedBean(StreamsBuilderFactoryBean streamsBuilderFactoryBean) {
    this.streamsBuilderFactoryBean = streamsBuilderFactoryBean;
  }

  @ManagedAttribute(description = "Get the topology description")
  public String getTopologyDescription() {
    var topology = streamsBuilderFactoryBean.getTopology();
    return (topology != null) ? topology.describe().toString() : "";
  }
}
