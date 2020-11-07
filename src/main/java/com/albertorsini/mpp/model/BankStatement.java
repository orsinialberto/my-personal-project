package com.albertorsini.mpp.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bank_statement")
public class BankStatement {

  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "wallet_id")
  private String walletId;

  @Column(name = "path")
  private String path;

  @Column(name = "registered_at")
  private Date registeredAt;

  @Column(name = "updated_at")
  private Date updatedAt;

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

  public String getWalletId() {
    return walletId;
  }

  public void setWalletId(String walletId) {
    this.walletId = walletId;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
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
