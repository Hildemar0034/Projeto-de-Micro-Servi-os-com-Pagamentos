package com.ifba.user.web;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @GetMapping("/{id}")
    public Map<String, Object> get(@PathVariable Long id){
        return Map.of("id", id, "name", "User-"+id, "tier", id % 2 == 0 ? "VIP":"BASIC");
    }
}
