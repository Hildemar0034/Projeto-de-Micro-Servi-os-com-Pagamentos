package com.example;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

  private final OrderOrchestrator orchestrator;

  public OrderController(OrderOrchestrator orchestrator) {
    this.orchestrator = orchestrator;
  }

  @PostMapping
  public ResponseEntity<Map<String,Object>> create(@RequestParam Long userId,
                                                   @RequestParam double amount) {
    return ResponseEntity.ok(orchestrator.createOrder(userId, amount));
  }
}
