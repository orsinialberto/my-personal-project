package com.albertorsini.mpp.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "event")
public class Event {

  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "wallet_id")
  private String walletId;

  @Column(name = "amount")
  private Double amount;

  @Column(name = "description")
  private String description;

  @Column(name = "tag")
  private String tag;

  @Column(name = "event_type")
  private EventType type;

  @Column(name = "currency")
  private Currency currency;

  @Column(name = "accounting_date")
  private Date accountingDate;

  @Column(name = "value_date")
  private Date valueDate;

  @Column(name = "registered_at")
  private Date registeredAt;

  @Column(name = "updated_at")
  private Date updatedAt;

  public Event() {
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

  public String getWalletId() {
    return walletId;
  }

  public void setWalletId(String walletId) {
    this.walletId = walletId;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public EventType getType() {
    return type;
  }

  public void setType(EventType type) {
    this.type = type;
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  public Date getAccountingDate() {
    return accountingDate;
  }

  public void setAccountingDate(Date accountingDate) {
    this.accountingDate = accountingDate;
  }

  public Date getValueDate() {
    return valueDate;
  }

  public void setValueDate(Date valueDate) {
    this.valueDate = valueDate;
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
