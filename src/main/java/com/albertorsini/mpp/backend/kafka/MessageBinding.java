package com.albertorsini.mpp.backend.kafka;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;

public interface MessageBinding {

  String REQUESTS_IN = "events";

  @Input(REQUESTS_IN)
  KStream<String, String> requestsIn();
}
