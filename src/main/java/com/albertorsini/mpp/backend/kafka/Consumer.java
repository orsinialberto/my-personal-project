package com.albertorsini.mpp.backend.kafka;

import com.albertorsini.mpp.engine.service.EventService;
import com.albertorsini.mpp.engine.service.WalletService;
import com.albertorsini.mpp.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Consumer {

  private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

  private final EventService eventService;
  private final WalletService walletService;

  public Consumer(final EventService eventService, final WalletService walletService) {
    this.eventService = eventService;
    this.walletService = walletService;
  }

  @KafkaListener(topics = "events", groupId = "wallet", containerFactory = "kafkaListenerContainerFactory")
  public void consume(@Payload final List<Event> message) {

    LOGGER.info("CONSUMER has consumed message: {}", message.size());

    eventService.saveAndIndex(message);

    message.forEach(event -> walletService.updateTotals(event.getWalletId(), event.getAmount(), event.getType()));
  }

}
