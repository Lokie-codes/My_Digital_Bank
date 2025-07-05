package com.bank.account.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class EventPublisherService {
  private final KafkaTemplate<String, Object> kafka;

  public EventPublisherService(KafkaTemplate<String,Object> kafka) {
    this.kafka = kafka;
  }

  public void publishAccountUpdated(String accountId, double newBalance) {
    Map<String,Object> evt = Map.of(
      "accountId", accountId,
      "newBalance", newBalance
    );
    kafka.send("account-updates", accountId, evt);
  }
}
