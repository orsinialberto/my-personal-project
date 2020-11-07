package com.albertorsini.mpp.backend.kafka;

import com.albertorsini.mpp.engine.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
public class Producer<T> {

  private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

  private final KafkaTemplate<String, String> kafkaTemplate;

  public Producer(final KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendMessageToTopic(final T message, final String topic) {

    requireNonNull(topic, "topic must not be null");
    requireNonNull(message, "message must not be null");

    final String kafkaMessage = Utils.convertToJson(message);

    LOGGER.info("send message {} to topic {}", kafkaMessage, topic);

    this.kafkaTemplate.send(topic, kafkaMessage);
  }
}
