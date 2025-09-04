package com.ifba.order.web;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final RestTemplate rt;
    public OrderController(RestTemplate rt){ this.rt = rt; }

    @Value("${user.service.url:http://localhost:8081}")
    private String userUrl;
    @Value("${payment.service.url:http://localhost:8083}")
    private String paymentUrl;

    @PostMapping("/create")
    public Map<String,Object> create(@RequestParam Long userId, @RequestParam double amount,
                                     @RequestParam(defaultValue="false") boolean slowPayment,
                                     @RequestParam(defaultValue="false") boolean failPayment,
                                     @RequestParam(defaultValue="CARD") String paymentMethod){
        // busca usuário (sem fallback)
        Map user = rt.getForObject(userUrl + "/users/" + userId, Map.class);
        // chama pagamento (sem circuit breaker)
        Map payment = rt.postForObject(paymentUrl + "/payments/pay?orderId={oid}&amount={amt}&slow={slow}&fail={fail}&paymentMethod={pm}",
                null, Map.class,  System.currentTimeMillis(), amount, slowPayment, failPayment, paymentMethod);
        return Map.of("orderId", System.currentTimeMillis(), "user", user, "payment", payment);
    }
}
