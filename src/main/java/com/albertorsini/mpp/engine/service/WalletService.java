package com.albertorsini.mpp.engine.service;

import com.albertorsini.mpp.backend.elasticsearch.ElasticsearchClient;
import com.albertorsini.mpp.engine.Utils;
import com.albertorsini.mpp.engine.exception.WalletNotFoundException;
import com.albertorsini.mpp.model.EventType;
import com.albertorsini.mpp.model.Total;
import com.albertorsini.mpp.model.Wallet;
import com.albertorsini.mpp.model.WalletConfiguration;
import com.albertorsini.mpp.repository.WalletRepository;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Collections.emptyMap;
import static java.util.Objects.requireNonNull;

@Service
public class WalletService {

  private final WalletRepository walletRepository;
  private final ElasticsearchClient elasticsearchClient;
  private final String eventMapping;
  private final String indexDefaultSettings;

  public WalletService(
    final WalletRepository walletRepository,
    final ElasticsearchClient elasticsearchClient
  ) throws IOException {

    this.walletRepository = walletRepository;
    this.elasticsearchClient = elasticsearchClient;

    final ClassLoader classLoader = getClass().getClassLoader();
    final InputStream eventMappingInputStream = classLoader.getResourceAsStream("event-mapping.json");
    final InputStream indexSettingsInputStream = classLoader.getResourceAsStream("index-default-settings.json");

    indexDefaultSettings = StreamUtils.copyToString(indexSettingsInputStream, UTF_8);
    eventMapping = StreamUtils.copyToString(eventMappingInputStream, UTF_8);
  }

  public Wallet create(final WalletConfiguration walletConfiguration) {

    requireNonNull(walletConfiguration, "walletConfiguration must not be null");

    final String id = UUID.randomUUID().toString();
    walletConfiguration.setTotal(new Total(0.0, 0.0, 0.0));

    final Wallet wallet = walletRepository.save(new Wallet(id, Utils.convertToJson(walletConfiguration)));

    final String endpointIndex = UriComponentsBuilder.fromPath("/{index}")
      .build()
      .expand(id)
      .toString();

    elasticsearchClient.put(endpointIndex, emptyMap(), indexDefaultSettings);

    final String endpointMapping = UriComponentsBuilder.fromPath("/{index}/_mapping")
      .build()
      .expand(id)
      .toString();

    elasticsearchClient.put(endpointMapping, emptyMap(), eventMapping);

    return wallet;
  }

  public Wallet findOne(final String id) {

    requireNonNull(id, "wallet id must not be null");

    return walletRepository.findById(id)
      .orElseThrow(() -> new WalletNotFoundException(id));
  }

  public List<Wallet> findAll() {

    return IterableUtils.toList(walletRepository.findAll());
  }

  public Wallet update(final String walletId, final WalletConfiguration walletConfiguration) {

    requireNonNull(walletId, "walletId must not be null");
    requireNonNull(walletConfiguration, "walletConfiguration must not be null");

    final Wallet wallet = walletRepository.findById(walletId)
      .orElseThrow(() -> new WalletNotFoundException(walletId));

    wallet.setConfiguration(Utils.convertToJson(walletConfiguration));

    return walletRepository.save(wallet);
  }

  public void updateTotals(final String walletId, final Double amount, final EventType eventType) {

    requireNonNull(walletId, "walletId must not be null");
    requireNonNull(amount, "amount must not be null");

    final Wallet wallet = this.findOne(walletId);
    final WalletConfiguration walletConfiguration = Utils.convertToObject(wallet.getConfiguration(), WalletConfiguration.class);

    final Total total = walletConfiguration.getTotal();
    total.updateTotals(amount, eventType);

    this.update(wallet.getId(), walletConfiguration);
  }

  public void delete(final String id) {

    requireNonNull(id, "wallet id must not be null");

    final Wallet wallet = this.findOne(id);

    final String endpoint = UriComponentsBuilder.fromPath("/{index}")
      .build()
      .expand(id)
      .toString();

    elasticsearchClient.delete(endpoint);
    walletRepository.delete(wallet);
  }
}
