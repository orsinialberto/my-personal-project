package com.albertorsini.mpp.backend.kafka;

import com.albertorsini.mpp.engine.Utils;
import com.albertorsini.mpp.engine.service.EventService;
import com.albertorsini.mpp.engine.service.WalletService;
import com.albertorsini.mpp.model.Event;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Service
public class EventConsumerStream {

  private static final Logger LOGGER = LoggerFactory.getLogger(EventConsumerStream.class);

  private final EventService eventService;
  private final WalletService walletService;

  public EventConsumerStream(final EventService eventService, final WalletService walletService) {
    this.eventService = eventService;
    this.walletService = walletService;
  }

  @StreamListener
  public void requestListener(final @Input(MessageBinding.REQUESTS_IN) KStream<String, String> requestsIn) {
    requestsIn.foreach((key, value) -> {
      final Event event = Utils.convertToObject(value, Event.class);
      eventService.saveAndIndex(event);
      walletService.updateTotals(event.getWalletId(), event.getAmount(), event.getType());
    });
  }

}
