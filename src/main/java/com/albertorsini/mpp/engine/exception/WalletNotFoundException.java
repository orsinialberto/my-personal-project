package com.albertorsini.mpp.engine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WalletNotFoundException extends RuntimeException {

  public WalletNotFoundException(final String walletId) {
    super("wallet not found " + walletId);
  }
}
