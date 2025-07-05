package com.bank.notification.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.bank.notification.service.AlertService;
import java.util.Map;

@Component
public class TransactionListener {
    private final AlertService alertSvc;

    public TransactionListener(AlertService alertSvc) {
        this.alertSvc = alertSvc;
    }

    @KafkaListener(topics = "transactions", groupId = "notification-service")
    public void onTransaction(Map<String,Object> event) {
        // you could enrich event (e.g. look up user email by accountId)
        alertSvc.sendTransactionAlert(event);
    }
}
