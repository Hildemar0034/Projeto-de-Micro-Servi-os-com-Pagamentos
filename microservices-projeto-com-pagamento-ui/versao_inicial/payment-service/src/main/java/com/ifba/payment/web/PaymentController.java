package com.ifba.payment.web;
import java.util.Map;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @PostMapping("/pay")
    public Map<String,Object> pay(@RequestParam Long orderId, @RequestParam double amount,
                                  @RequestParam(defaultValue="false") boolean slow,
                                  @RequestParam(defaultValue="false") boolean fail,
                                  @RequestParam(defaultValue="CARD") String paymentMethod) throws Exception {
        if (slow) {
            TimeUnit.SECONDS.sleep(5); // simula lentidão
        }
        if (fail) {
            throw new RuntimeException("Simulated payment failure");
        }
        return Map.of("orderId", orderId, "status", "PAID", "amount", amount, "paymentMethod", paymentMethod);
    }

    @GetMapping("/healthcheck")
    public Map<String,String> health(){ return Map.of("status","OK"); }
}
