package com.albertorsini.expense.engine;

import com.albertorsini.expense.model.Wallet;
import com.albertorsini.expense.model.WalletConfiguration;
import com.albertorsini.expense.repository.WalletRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

@Service
public class WalletService {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  private final WalletRepository walletRepository;

  public WalletService(WalletRepository walletRepository) {
    this.walletRepository = walletRepository;
  }

  public Wallet create(final WalletConfiguration walletConfiguration) throws JsonProcessingException {

    requireNonNull(walletConfiguration, "walletConfiguration must not be null");

    final Wallet wallet = new Wallet(
      UUID.randomUUID().toString(),
      MAPPER.writeValueAsString(walletConfiguration)
    );

    return walletRepository.save(wallet);
  }

  public Wallet findOne(final String id) {

    requireNonNull(id, "wallet id must not be null");

    return walletRepository.findById(id)
      .orElseThrow(() -> new RuntimeException(format("wallet %s not found", id)));
  }

  public List<Wallet> findAll() {

    return IterableUtils.toList(walletRepository.findAll());
  }

  public Wallet update(final Wallet wallet) {

    requireNonNull(wallet, "wallet must not be null");

    final Wallet walletToUpdate = walletRepository
      .findById(wallet.getId()).orElseThrow(() -> new RuntimeException(format("wallet %s not found", wallet.getId())));

    walletToUpdate.setConfiguration(wallet.getConfiguration());

    return walletRepository.save(walletToUpdate);
  }

  public void delete(final String id) {

    requireNonNull(id, "wallet id must not be null");

    final Wallet wallet = walletRepository
      .findById(id).orElseThrow(() -> new RuntimeException(format("wallet %s not found", id)));

    walletRepository.delete(wallet);
  }
}
