# Microservices com Pagamento — Versões e Execução

Este repositório contém:
- `versao_inicial/` 
- `versao_refatorada/` 
- `versao_refatorada_resiliente/` (**nova**, com API Gateway + OpenFeign (IoC) + Circuit Breaker + Bulkhead)
- `UML.md` com diagramas Antes/Depois (Mermaid)
- `Relatorio.pdf` (7+ páginas)

## Como executar a versão resiliente

Pré-requisitos: Java 17+, Maven 3.9+

Terminal 1 (Gateway):
```bash
cd versao_refatorada_resiliente/api-gateway
mvn spring-boot:run
```

Terminal 2 (User):
```bash
cd versao_refatorada_resiliente/user-service
mvn spring-boot:run
```

Terminal 3 (Payment):
```bash
cd versao_refatorada_resiliente/payment-service
mvn spring-boot:run
```

Terminal 4 (Order):
```bash
cd versao_refatorada_resiliente/order-service
mvn spring-boot:run
```

### Testes rápidos (via Gateway)
Criar pedido (passará pelo circuito/bulkhead quando houver falhas/latência simuladas):
```bash
curl -X POST "http://localhost:8080/orders?userId=1&amount=149.9"
```

Consultar usuário:
```bash
curl "http://localhost:8080/users/1"
```

Cobrança direta (para observar falhas/latência aleatória):
```bash
curl -X POST "http://localhost:8080/payments/charge" -H "Content-Type: application/json" -d '{"userId":1,"amount":149.9}'
```

## O que foi implementado
- **API Gateway** (Spring Cloud Gateway)
- **IoC/Desacoplamento** com **OpenFeign** nos clients de `order-service`
- **Circuit Breaker** (Resilience4j) com `paymentCB` e fallback
- **Bulkhead** (Resilience4j) com `paymentBH`
- **Actuator** exposto para diagnóstico básico
- **Diagramas UML (antes e depois da refatoração)** em **PlantUML**
  - Arquivos: `uml-antes.puml` e `uml-depois.puml` (na raiz do projeto)
  - Mostram claramente a evolução da arquitetura
- **Relatório técnico** em PDF (`Relatorio.pdf`)
  - Diagnóstico da versão inicial
  - Identificação de anti-patterns e violações SOLID
  - Justificativas das refatorações
  - Comparação entre arquiteturas
  - Conclusões e próximos passos
