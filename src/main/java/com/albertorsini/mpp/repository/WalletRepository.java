package com.albertorsini.mpp.repository;

import com.albertorsini.mpp.model.Wallet;
import org.springframework.data.repository.CrudRepository;

public interface WalletRepository extends CrudRepository<Wallet, String> {
}
