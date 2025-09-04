package com.example;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@FeignClient(name="userClient", url="${user.service.url}")
interface UserClient {
  @GetMapping("/users/{id}")
  Map<String,Object> get(@PathVariable("id") Long id);
}

@FeignClient(name="paymentClient", url="${payment.service.url}")
interface PaymentClient {
  @PostMapping("/payments/charge")
  Map<String,Object> charge(@RequestBody Map<String,Object> payload);
}
