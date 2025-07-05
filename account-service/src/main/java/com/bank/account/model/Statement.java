package com.bank.account.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "statements")
public class Statement {
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String accountId;
  private String type; // DEBIT or CREDIT
  private double amount;
  private Instant timestamp;

  public Statement() {}
  public Statement(String accountId, String type, double amount, Instant ts) {
    this.accountId = accountId;
    this.type      = type;
    this.amount    = amount;
    this.timestamp = ts;
  }

  // Getters / Setters omitted for brevity
}
