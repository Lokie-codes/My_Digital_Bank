package com.bank.transaction.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    private String id;

    private String accountId;
    private String type;      // "DEBIT" or "CREDIT"
    private double amount;
    private Instant timestamp;

    public Transaction() {
        this.id = UUID.randomUUID().toString();
        this.timestamp = Instant.now();
    }

    public Transaction(String accountId, String type, double amount) {
        this();
        this.accountId = accountId;
        this.type      = type;
        this.amount    = amount;
    }

    // Getters and setters omitted for brevity …
    public String getId() { return id; }
    public String getAccountId() { return accountId; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public Instant getTimestamp() { return timestamp; }
}
