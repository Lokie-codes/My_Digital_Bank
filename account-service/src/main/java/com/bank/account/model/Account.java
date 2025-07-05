package com.bank.account.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "accounts")
public class Account {
  @Id
  private String id;
  private double balance;

  // Constructors
  public Account() {}
  public Account(String id, double balance) {
    this.id = id;
    this.balance = balance;
  }

  // Getters / Setters
  public String getId() { return id; }
  public void setId(String id) { this.id = id; }
  public double getBalance() { return balance; }
  public void setBalance(double balance) { this.balance = balance; }
}
