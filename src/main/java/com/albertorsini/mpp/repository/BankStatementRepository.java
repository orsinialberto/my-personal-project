package com.albertorsini.mpp.repository;

import com.albertorsini.mpp.model.BankStatement;
import org.springframework.data.repository.CrudRepository;

public interface BankStatementRepository extends CrudRepository<BankStatement, String> {

  void deleteByWalletId(final String walletId);
}
