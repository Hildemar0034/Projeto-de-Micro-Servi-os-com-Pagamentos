# Projeto Microservices Resilientes (Java + Spring Boot)

Este pacote contém **duas versões** conforme o enunciado:
- `versao_inicial/` — arquitetura com falhas propositais.
- `versao_refatorada/` — com **API Gateway**, **Circuit Breaker**, **Bulkhead** e **IoC (Feign)**.

Requisitos locais:
- **Java 17+** (JDK)
- **Maven 3.9+**
- Sistema: testado para Windows (scripts `run-windows.cmd` inclusos).

## Como executar (passo a passo)

### 1) Versão inicial
Abra 3 terminais (ou rode sequencialmente):
1. `cd versao_inicial\user-service && run-windows.cmd`
2. `cd versao_inicial\payment-service && run-windows.cmd`
3. `cd versao_inicial\order-service && run-windows.cmd`

Testes rápidos:
- Usuário: `http://localhost:8081/users/1`
- Pedido (sem resiliência): `POST http://localhost:8082/orders/create?userId=1&amount=50.0&slowPayment=true` (tende a travar quando `slowPayment=true`).

### 2) Versão refatorada
Abra 4 terminais:
1. `cd versao_refatorada\user-service && run-windows.cmd`
2. `cd versao_refatorada\payment-service && run-windows.cmd`
3. `cd versao_refatorada\order-service && run-windows.cmd`
4. `cd versao_refatorada\api-gateway && run-windows.cmd`

Acesse a UI simples do Gateway: `http://localhost:8080/`

Endpoints pelo Gateway (externo):
- `GET http://localhost:8080/users/1`
- `POST http://localhost:8080/orders/create?userId=1&amount=50.0&slowPayment=true`  
  - Com `slowPayment=true` o **Circuit Breaker** pode abrir após falhas/lentidão.
  - Com `failPayment=true` ativa o fallback.

### Observações
- As portas são fixas para simplicidade (sem service discovery).
- Configurações de resiliência estão em `order-service/src/main/resources/application.yml` (versão refatorada).
- Você pode ajustar as janelas do Circuit Breaker e limites do Bulkhead.
