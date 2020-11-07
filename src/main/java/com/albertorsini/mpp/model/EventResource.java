package com.albertorsini.mpp.model;

import java.util.Date;
import java.util.Set;

public class EventResource {

  private String id;

  private Double amount;

  private String walletId;

  private String description;

  private Set<String> tag;

  private EventType eventType;

  private Currency currency;

  private Date accountingDate;

  private Date valueDate;

  private Date registeredAt;

  private Date updatedAt;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public String getWalletId() {
    return walletId;
  }

  public void setWalletId(String walletId) {
    this.walletId = walletId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Set<String> getTag() {
    return tag;
  }

  public void setTag(Set<String> tag) {
    this.tag = tag;
  }

  public EventType getEventType() {
    return eventType;
  }

  public void setEventType(EventType eventType) {
    this.eventType = eventType;
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
