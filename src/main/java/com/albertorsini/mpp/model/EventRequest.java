package com.albertorsini.mpp.model;

import java.util.Date;
import java.util.Set;

public class EventRequest {

  private Double amount;

  private String description;

  private Set<String> tag;

  private EventType type;

  private Currency currency;

  private Date accountingDate;

  private Date valueDate;

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

  public Set<String> getTag() {
    return tag;
  }

  public void setTag(Set<String> tag) {
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
}
