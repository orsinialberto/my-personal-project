package com.albertorsini.mpp.backend.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfiguration {

  @Value("${mpp.elasticsearch.url}")
  private String url;

  @Bean
  public ElasticsearchClient elasticsearchClient() {

    final RestClient restClient = RestClient.builder(HttpHost.create(url)).build();

    return new ElasticsearchClient(restClient);
  }
}
