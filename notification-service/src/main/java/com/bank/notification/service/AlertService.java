package com.bank.notification.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class AlertService {
    private final JavaMailSender mailSender;

    public AlertService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendTransactionAlert(Map<String,Object> event) {
        // Example: event contains transactionId, accountId, amount, type, timestamp
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo((String)event.getOrDefault("email","user@example.com"));
        msg.setSubject("New Transaction: " + event.get("type"));
        msg.setText(
          "Transaction ID: " + event.get("transactionId") + "\n" +
          "Account ID:     " + event.get("accountId")   + "\n" +
          "Type:           " + event.get("type")        + "\n" +
          "Amount:         " + event.get("amount")      + "\n" +
          "Timestamp:      " + event.get("timestamp")
        );
        mailSender.send(msg);
    }

    public void sendAccountUpdateAlert(Map<String,Object> event) {
        // event: accountId, newBalance
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo((String)event.getOrDefault("email","user@example.com"));
        msg.setSubject("Balance Updated");
        msg.setText(
          "Account ID: " + event.get("accountId") + "\n" +
          "New Balance: " + event.get("newBalance")
        );
        mailSender.send(msg);
    }
}
