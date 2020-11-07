package com.albertorsini.mpp.web.assembler;

import com.albertorsini.mpp.engine.Utils;
import com.albertorsini.mpp.model.Wallet;
import com.albertorsini.mpp.model.WalletConfiguration;
import com.albertorsini.mpp.model.WalletResource;
import org.springframework.stereotype.Service;

@Service
public class WalletResourceAssembler implements ResourceAssembler<Wallet, WalletResource> {

  @Override
  public WalletResource toResource(final Wallet wallet) {

    return new WalletResource(
      wallet.getId(),
      Utils.convertToObject(wallet.getConfiguration(), WalletConfiguration.class),
      wallet.getRegisteredAt(),
      wallet.getUpdatedAt()
    );
  }
}
