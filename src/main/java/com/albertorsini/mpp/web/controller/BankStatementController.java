package com.albertorsini.mpp.web.controller;

import com.albertorsini.mpp.engine.service.BankStatementService;
import com.albertorsini.mpp.model.BankStatement;
import com.albertorsini.mpp.model.BankStatementRequest;
import com.albertorsini.mpp.model.BankStatementResource;
import com.albertorsini.mpp.web.assembler.BankStatementResourceAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet/{walletId}/bank-statement")
public class BankStatementController {

  private static final Logger LOGGER = LoggerFactory.getLogger(BankStatementController.class);

  private final BankStatementService bankStatementService;
  private final BankStatementResourceAssembler bankStatementResourceAssembler;

  public BankStatementController(
    final BankStatementService bankStatementService,
    final BankStatementResourceAssembler bankStatementResourceAssembler
  ) {
    this.bankStatementService = bankStatementService;
    this.bankStatementResourceAssembler = bankStatementResourceAssembler;
  }

  @PostMapping
  public BankStatementResource create(final @PathVariable String walletId, final @RequestBody BankStatementRequest bankStatementRequest) {

    LOGGER.info("creating bank statement");

    final BankStatement bankStatement = bankStatementService.create(bankStatementRequest, walletId);

    LOGGER.info("created bank statement");

    return bankStatementResourceAssembler.toResource(bankStatement);
  }
}
