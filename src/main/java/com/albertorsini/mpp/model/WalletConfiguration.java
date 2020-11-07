package com.albertorsini.mpp.model;

import java.util.Set;

public class WalletConfiguration {

  private String name;

  private Set<String> tags;

  private Total total;

  public WalletConfiguration() {
  }

  public WalletConfiguration(String name, Set<String> tags, Total total) {
    this.name = name;
    this.tags = tags;
    this.total = total;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<String> getTags() {
    return tags;
  }

  public void setTags(Set<String> tags) {
    this.tags = tags;
  }

  public Total getTotal() {
    return total;
  }

  public void setTotal(Total total) {
    this.total = total;
  }
}
