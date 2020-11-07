package com.albertorsini.expense.repository;

import com.albertorsini.expense.model.Wallet;
import org.springframework.data.repository.CrudRepository;

public interface WalletRepository extends CrudRepository<Wallet, String> {
}
