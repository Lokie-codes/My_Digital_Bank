package com.bank.account.service;

import com.bank.account.model.Account;
import com.bank.account.model.Statement;
import com.bank.account.repository.AccountRepository;
import com.bank.account.repository.StatementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class AccountService {
  private final AccountRepository acctRepo;
  private final StatementRepository stmtRepo;
  private final EventPublisherService publisher;

  public AccountService(
    AccountRepository acctRepo,
    StatementRepository stmtRepo,
    EventPublisherService publisher
  ) {
    this.acctRepo  = acctRepo;
    this.stmtRepo  = stmtRepo;
    this.publisher = publisher;
  }

  public double getBalance(String accountId) {
    return acctRepo.findById(accountId)
                   .orElse(new Account(accountId, 0))
                   .getBalance();
  }

  public List<Statement> listStatements(String accountId) {
    return stmtRepo.findByAccountIdOrderByTimestampDesc(accountId);
  }

  @Transactional
  public double updateBalance(String accountId, double amount, String type) {
    Account acct = acctRepo.findById(accountId)
      .orElse(new Account(accountId, 0));
    double newBal = acct.getBalance() + ("DEBIT".equals(type) ? -amount : amount);
    acct.setBalance(newBal);
    acctRepo.save(acct);

    Statement stmt = new Statement(accountId, type, amount, Instant.now());
    stmtRepo.save(stmt);

    publisher.publishAccountUpdated(accountId, newBal);
    return newBal;
  }
}
