package com.albertorsini.mpp.backend.elasticsearch;

import org.apache.http.HttpEntity;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static java.util.Objects.requireNonNull;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.PUT;

public class ElasticsearchClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchClient.class);

  private final RestClient restClient;

  public ElasticsearchClient(final RestClient restClient) {
    this.restClient = restClient;
  }

  public void put(final String endpoint, final Map<String, String> params, final String body) {

    requireNonNull(endpoint, "endpoint must be not null");
    requireNonNull(body, "body must be not null");

    LOGGER.debug("PUT elastic request \n{}", body);

    performRequest(PUT, endpoint, params, body);
  }

  public void delete(final String endpoint) {

    requireNonNull(endpoint, "endpoint must be not null");

    performRequest(DELETE, endpoint, emptyMap(), null);
  }

  public void index(final String body) {

    requireNonNull(body, "body must be not null");

    final String endpoint = "/_bulk";

    this.put(endpoint, emptyMap(), body);
  }

  private void performRequest(final HttpMethod method, final String endpoint, final Map<String, String> params, final String body) {

    requireNonNull(method, "method must be not null");
    requireNonNull(endpoint, "endpoint must be not null");

    try {
      final Request request = new Request(method.toString(), endpoint);

      if (body != null) {
        final HttpEntity entity = new NStringEntity(body, APPLICATION_JSON);
        request.setEntity(entity);
      }

      request.addParameters(params);

      restClient.performRequest(request);

    } catch (final IOException e) {
      throw new ElasticsearchIOException(e);
    }
  }
}
