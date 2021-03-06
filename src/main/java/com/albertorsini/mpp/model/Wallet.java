package com.albertorsini.mpp.model;

import com.albertorsini.mpp.engine.Utils;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "wallet")
public class Wallet {

  @Id
  @Column(name = "id")
  private String id;

  @Lob
  @Column(name = "configuration")
  private String configuration;

  @Column(name = "registered_at")
  private Date registeredAt;

  @Column(name = "updated_at")
  private Date updatedAt;

  public Wallet() {
  }

  public Wallet(final String id, final String configuration) {
    this.id = id;
    this.configuration = configuration;
  }

  public Wallet(final WalletResource walletResource) {
    this.id = walletResource.getId();
    this.configuration = Utils.convertToJson(walletResource.getConfiguration());
    this.registeredAt = walletResource.getRegisteredAt();
  }

  @PreUpdate
  public void preUpdate() {
    updatedAt = new Date();
  }

  @PrePersist
  public void prePersist() {
    final Date now = new Date();
    registeredAt = now;
    updatedAt = now;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getConfiguration() {
    return configuration;
  }

  public void setConfiguration(String configuration) {
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
