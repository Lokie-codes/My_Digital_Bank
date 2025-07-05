package com.bank.account.repository;

import com.bank.account.model.Statement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StatementRepository extends JpaRepository<Statement, Long> {
  List<Statement> findByAccountIdOrderByTimestampDesc(String accountId);
}
