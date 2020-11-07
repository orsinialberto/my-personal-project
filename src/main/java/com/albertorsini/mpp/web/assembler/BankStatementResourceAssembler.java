package com.albertorsini.mpp.web.assembler;

import com.albertorsini.mpp.model.BankStatement;
import com.albertorsini.mpp.model.BankStatementResource;
import org.springframework.stereotype.Service;

@Service
public class BankStatementResourceAssembler implements ResourceAssembler<BankStatement, BankStatementResource> {

  @Override
  public BankStatementResource toResource(final BankStatement bankStatement) {

    final BankStatementResource bankStatementResource = new BankStatementResource();
    bankStatementResource.setId(bankStatement.getId());
    bankStatementResource.setPath(bankStatement.getPath());
    bankStatementResource.setWalletId(bankStatement.getWalletId());
    bankStatementResource.setRegisteredAt(bankStatement.getRegisteredAt());
    bankStatementResource.setUpdatedAt(bankStatement.getUpdatedAt());

    return bankStatementResource;
  }

}
