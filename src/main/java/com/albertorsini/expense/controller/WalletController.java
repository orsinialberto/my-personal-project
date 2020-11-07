package com.albertorsini.expense.controller;

import com.albertorsini.expense.engine.WalletService;
import com.albertorsini.expense.model.Wallet;
import com.albertorsini.expense.model.WalletConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wallet")
public class WalletController {

  private static final Logger LOGGER = LoggerFactory.getLogger(WalletController.class);

  private final WalletService walletService;

  public WalletController(WalletService walletService) {
    this.walletService = walletService;
  }

  @PostMapping
  public Wallet create(final @RequestBody WalletConfiguration walletConfiguration) throws JsonProcessingException {

    LOGGER.info("creating wallet {}", walletConfiguration.getName());

    final Wallet wallet = walletService.create(walletConfiguration);

    LOGGER.info("created wallet {}", wallet.getId());

    return wallet;
  }

  @GetMapping("/{id}")
  public Wallet get(final @PathVariable String id) {

    LOGGER.info("reading provisioning configuration of wallet {}", id);

    return walletService.findOne(id);
  }

  @GetMapping
  public List<Wallet> getAll() {

    LOGGER.info("reading all provisioning configurations");

    return walletService.findAll();
  }

  @PutMapping
  public Wallet update(final @RequestBody Wallet walletRequest) {

    LOGGER.info("updating configuration of wallet {}", walletRequest.getId());

    final Wallet wallet = walletService.update(walletRequest);

    LOGGER.info("updated configuration of wallet {}", wallet.getId());

    return wallet;
  }

  @DeleteMapping("/{id}")
  public String delete(final @PathVariable String id) {

    LOGGER.info("deleting wallet {}", id);

    walletService.delete(id);

    LOGGER.info("deleted wallet {}", id);

    return id;
  }


}
