package com.bank.notification.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.bank.notification.service.AlertService;
import java.util.Map;

@Component
public class AccountUpdateListener {
    private final AlertService alertSvc;

    public AccountUpdateListener(AlertService alertSvc) {
        this.alertSvc = alertSvc;
    }

    @KafkaListener(topics = "account-updates", groupId = "notification-service")
    public void onAccountUpdate(Map<String,Object> event) {
        alertSvc.sendAccountUpdateAlert(event);
    }
}
