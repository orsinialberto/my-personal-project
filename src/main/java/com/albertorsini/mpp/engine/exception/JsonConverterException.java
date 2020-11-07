package com.albertorsini.mpp.engine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class JsonConverterException extends RuntimeException {

  public JsonConverterException(final Exception e) {
    super("error converting json", e);
  }
}
