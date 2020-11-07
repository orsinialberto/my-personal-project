package com.albertorsini.mpp.web.controller;

import com.albertorsini.mpp.engine.service.BankStatementService;
import com.albertorsini.mpp.engine.service.EventService;
import com.albertorsini.mpp.engine.service.WalletService;
import com.albertorsini.mpp.model.Wallet;
import com.albertorsini.mpp.model.WalletConfiguration;
import com.albertorsini.mpp.model.WalletResource;
import com.albertorsini.mpp.web.assembler.WalletResourceAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wallet")
public class WalletController {

  private static final Logger LOGGER = LoggerFactory.getLogger(WalletController.class);

  private final WalletService walletService;
  private final EventService eventService;
  private final BankStatementService bankStatementService;
  private final WalletResourceAssembler walletResourceAssembler;

  public WalletController(
    final WalletService walletService,
    final EventService eventService,
    final BankStatementService bankStatementService,
    final WalletResourceAssembler walletResourceAssembler
  ) {
    this.walletService = walletService;
    this.eventService = eventService;
    this.bankStatementService = bankStatementService;
    this.walletResourceAssembler = walletResourceAssembler;
  }

  @PostMapping
  public WalletResource create(final @RequestBody WalletConfiguration walletConfiguration) {

    LOGGER.info("creating wallet {}", walletConfiguration.getName());

    final Wallet wallet = walletService.create(walletConfiguration);

    LOGGER.info("created wallet {}", wallet.getId());

    return walletResourceAssembler.toResource(wallet);
  }

  @GetMapping("/{id}")
  public WalletResource get(final @PathVariable String id) {

    LOGGER.info("reading provisioning configuration of wallet {}", id);

    return walletResourceAssembler.toResource(walletService.findOne(id));
  }

  @GetMapping
  public List<WalletResource> getAll() {

    LOGGER.info("reading all provisioning configurations");

    return walletResourceAssembler.toResource(walletService.findAll());
  }

  @PutMapping
  public WalletResource update(final @RequestBody WalletResource walletResource) {

    LOGGER.info("updating configuration of wallet {}", walletResource.getId());

    final Wallet wallet = walletService.update(walletResource.getId(), walletResource.getConfiguration());

    LOGGER.info("updated configuration of wallet {}", wallet.getId());

    return walletResourceAssembler.toResource(wallet);
  }

  @DeleteMapping("/{id}")
  public String delete(final @PathVariable String id) {

    LOGGER.info("deleting wallet {}", id);

    eventService.deleteAll(id);
    walletService.delete(id);
    bankStatementService.deleteByWalletId(id);

    LOGGER.info("deleted wallet {}", id);

    return id;
  }


}
