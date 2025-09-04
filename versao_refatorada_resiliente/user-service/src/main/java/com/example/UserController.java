package com.example;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

  @GetMapping("/{id}")
  public ResponseEntity<Map<String, Object>> get(@PathVariable Long id) {
    Map<String, Object> user = new HashMap<>();
    user.put("id", id);
    user.put("name", "User-" + id);
    user.put("status", "ACTIVE");

    return ResponseEntity.ok(user);
  }
}
