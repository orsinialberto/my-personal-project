package com.albertorsini.mpp.model;

import java.util.Map;

public class Total {

  private Double deficit;

  private Double totalExpense;

  private Double totalIncome;

  private Map<String, Double> totalTag;

  public Total() {
  }

  public Total(Double deficit, Double totalExpense, Double totalIncome) {
    this.deficit = deficit;
    this.totalExpense = totalExpense;
    this.totalIncome = totalIncome;
  }

  public Double getDeficit() {
    return deficit;
  }

  public void setDeficit(Double deficit) {
    this.deficit = deficit;
  }

  public Double getTotalExpense() {
    return totalExpense;
  }

  public void setTotalExpense(Double totalExpense) {
    this.totalExpense = totalExpense;
  }

  public Double getTotalIncome() {
    return totalIncome;
  }

  public void setTotalIncome(Double totalIncome) {
    this.totalIncome = totalIncome;
  }

  public Map<String, Double> getTotalTag() {
    return totalTag;
  }

  public void setTotalTag(Map<String, Double> totalTag) {
    this.totalTag = totalTag;
  }

  public void updateTotals(Double amount, EventType eventType) {

    if (eventType.equals(EventType.EXPENSE)) {
      this.totalExpense += amount;
    } else {
      this.totalIncome += amount;
    }

    this.deficit += amount;
  }
}
