package com.albertorsini.mpp.engine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class BankStatementProcessingException extends RuntimeException {

  public BankStatementProcessingException(final Exception e, final String filename) {
    super("error processing file " + filename, e);
  }
}
