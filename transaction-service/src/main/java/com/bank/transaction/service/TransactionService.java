package com.bank.transaction.service;

import com.bank.transaction.model.Transaction;
import com.bank.transaction.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.kafka.core.KafkaTemplate;
import com.bank.account.grpc.AccountServiceGrpc;
import com.bank.account.grpc.AccountProto.UpdateBalanceRequest;
import com.bank.account.grpc.AccountProto.UpdateBalanceResponse;

import java.util.Map;

@Service
public class TransactionService {
    private final TransactionRepository txRepo;
    private final AccountServiceGrpc.AccountServiceBlockingStub accountStub;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public static class TransactionResult {
        private final String transactionId;
        private final double newBalance;
        public TransactionResult(String transactionId, double newBalance) {
            this.transactionId = transactionId;
            this.newBalance    = newBalance;
        }
        public String getTransactionId() { return transactionId; }
        public double getNewBalance()    { return newBalance;    }
    }

    public TransactionService(TransactionRepository txRepo,
                              AccountServiceGrpc.AccountServiceBlockingStub accountStub,
                              KafkaTemplate<String, Object> kafkaTemplate) {
        this.txRepo        = txRepo;
        this.accountStub   = accountStub;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public TransactionResult createTransaction(String accountId, double amount, String type) {
        // 1. Persist
        Transaction tx = new Transaction(accountId, type, amount);
        txRepo.save(tx);

        // 2. Call Account Service
        UpdateBalanceRequest req = UpdateBalanceRequest.newBuilder()
            .setAccountId(accountId)
            .setAmount(amount)
            .setType(type)
            .build();
        UpdateBalanceResponse res = accountStub.updateBalance(req);

        // 3. Publish Kafka event
        Map<String,Object> event = Map.of(
            "transactionId", tx.getId(),
            "accountId",     accountId,
            "amount",        amount,
            "type",          type,
            "timestamp",     tx.getTimestamp().toEpochMilli()
        );
        kafkaTemplate.send("transactions", accountId, event);

        return new TransactionResult(tx.getId(), res.getNewBalance());
    }
}
