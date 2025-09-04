# Versão Inicial (com falhas propositalmente)

- Chamadas diretas entre serviços (RestTemplate).
- Sem Circuit Breaker.
- Sem Bulkhead.
- Sem API Gateway.
- Sem IoC explícito além do básico do Spring.
- Portas:
  - user-service: 8081
  - payment-service: 8083
  - order-service: 8082 (consome user e payment)
