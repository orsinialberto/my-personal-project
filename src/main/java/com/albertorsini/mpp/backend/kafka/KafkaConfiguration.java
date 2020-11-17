package com.albertorsini.mpp.backend.kafka;

import com.albertorsini.mpp.model.Event;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class KafkaConfiguration {

  @Bean
  public ConsumerFactory<String, Event> consumerFactory(final KafkaProperties kafkaProperties) {

    final JsonDeserializer<Event> deserializer = new JsonDeserializer<>(Event.class);
    deserializer.setRemoveTypeHeaders(false);
    deserializer.addTrustedPackages("*");
    deserializer.setUseTypeMapperForKey(true);

    return new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties(), new StringDeserializer(), deserializer);
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, Event> kafkaListenerContainerFactory(final KafkaProperties kafkaProperties) {

    final ConcurrentKafkaListenerContainerFactory<String, Event> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory(kafkaProperties));
    factory.setBatchListener(true);

    return factory;
  }
}
