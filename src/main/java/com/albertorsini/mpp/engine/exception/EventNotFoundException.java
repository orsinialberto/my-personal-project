package com.albertorsini.mpp.engine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EventNotFoundException extends RuntimeException {

  public EventNotFoundException(final String eventId) {
    super("event not found: " + eventId);
  }
}
