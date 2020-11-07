package com.albertorsini.mpp.backend.elasticsearch;

public class ElasticsearchIOException extends RuntimeException {

  public ElasticsearchIOException(final Throwable cause) {
    super("IO exception calling elasticsearch REST API", cause);
  }
}
