package com.bank.transaction.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.bank.transaction.service.TransactionService;
import com.bank.transaction.service.TransactionService.TransactionResult;

import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService txService;

    public TransactionController(TransactionService txService) {
        this.txService = txService;
    }

    @PostMapping
    public ResponseEntity<TransactionResult> createTransaction(@RequestBody Map<String,Object> body) {
        String accountId = (String) body.get("accountId");
        double amount    = Double.parseDouble(body.get("amount").toString());
        String type      = (String) body.get("type");

        TransactionResult result = txService.createTransaction(accountId, amount, type);
        return ResponseEntity.ok(result);
    }
}
