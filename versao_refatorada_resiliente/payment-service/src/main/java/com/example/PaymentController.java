package com.example;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/payments")
public class PaymentController {

  @PostMapping("/charge")
  public ResponseEntity<Map<String, Object>> charge(@RequestBody Map<String,Object> body) throws InterruptedException {
    // simulate occasional latency/failure to trigger circuit breaker
    int r = ThreadLocalRandom.current().nextInt(10);
    if (r < 2) {
      Thread.sleep(2000);
    }
    if (r == 9) {
      throw new RuntimeException("Gateway error from payment provider");
    }

    Map<String, Object> response = new HashMap<>();
    response.put("status", "APPROVED");
    response.put("amount", body.getOrDefault("amount", BigDecimal.valueOf(100)));
    response.put("transactionId", "tx-" + System.currentTimeMillis());

    return ResponseEntity.ok(response);
  }
}
