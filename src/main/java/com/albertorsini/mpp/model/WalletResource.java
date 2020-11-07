package com.albertorsini.mpp.model;

import java.util.Date;

public class WalletResource {

  private String id;

  private WalletConfiguration configuration;

  private Date registeredAt;

  private Date updatedAt;

  public WalletResource() {

  }

  public WalletResource(String id, WalletConfiguration configuration, Date registeredAt, Date updatedAt) {
    this.id = id;
    this.configuration = configuration;
    this.registeredAt = registeredAt;
    this.updatedAt = updatedAt;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public WalletConfiguration getConfiguration() {
    return configuration;
  }

  public void setConfiguration(WalletConfiguration configuration) {
    this.configuration = configuration;
  }

  public Date getRegisteredAt() {
    return registeredAt;
  }

  public void setRegisteredAt(Date registeredAt) {
    this.registeredAt = registeredAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }
}
