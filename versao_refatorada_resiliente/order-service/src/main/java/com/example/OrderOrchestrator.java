package com.example;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderOrchestrator {

  private final UserClient users;
  private final PaymentClient payments;

  public OrderOrchestrator(UserClient users, PaymentClient payments) {
    this.users = users;
    this.payments = payments;
  }

  @CircuitBreaker(name="paymentCB", fallbackMethod="paymentFallback")
  @Bulkhead(name="paymentBH", type = Bulkhead.Type.SEMAPHORE)
  public Map<String,Object> createOrder(Long userId, double amount) {
    Map<String,Object> user = users.get(userId);
    Map<String,Object> chargeReq = new HashMap<>();
    chargeReq.put("userId", userId);
    chargeReq.put("amount", amount);
    Map<String,Object> charge = payments.charge(chargeReq);

    Map<String,Object> order = new HashMap<>();
    order.put("user", user);
    order.put("payment", charge);
    order.put("status", "CREATED");
    return order;
  }

  public Map<String,Object> paymentFallback(Long userId, double amount, Throwable t) {
    Map<String,Object> fallback = new HashMap<>();
    fallback.put("status", "PENDING_PAYMENT");
    fallback.put("reason", t.getClass().getSimpleName() + ": " + t.getMessage());
    fallback.put("userId", userId);
    fallback.put("amount", amount);
    return fallback;
  }
}
