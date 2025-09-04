# Diagramas UML (Antes e Depois)

## Antes (versão inicial)
@startuml
title Arquitetura ANTES da Refatoração

actor Cliente

rectangle "Serviço de Pedidos\n(Order Service)" as Pedido {
}

rectangle "Serviço de Usuários\n(User Service)" as Usuario {
}

rectangle "Serviço de Pagamentos\n(Payment Service)" as Pagamento {
}

Cliente --> Pedido : HTTP (POST /orders)
Pedido --> Usuario : REST (GET /users/{id})
Pedido --> Pagamento : REST (POST /payments/charge)

note right of Pedido
  - Centraliza a orquestração de pedidos
  - Chamadas REST diretas (sem abstração)
  - Forte acoplamento
  - Não há resiliência (falhas propagam)
end note

note right of Pagamento
  - Processa pagamentos
  - Qualquer falha ou lentidão quebra o fluxo
end note

note right of Usuario
  - Fornece dados de clientes
end note
@enduml



## Depois (versão refatorada resiliente)
@startuml
title Arquitetura DEPOIS da Refatoração (Resiliente)

actor Cliente

rectangle "API Gateway\n(Spring Cloud Gateway)" as Gateway {
}

rectangle "Serviço de Pedidos\n(Order Service)\ncom Feign + Resilience4j" as Pedido {
}

rectangle "Serviço de Usuários\n(User Service)" as Usuario {
}

rectangle "Serviço de Pagamentos\n(Payment Service)" as Pagamento {
}

rectangle "Mecanismos de Resiliência\n(Resilience4j)" as Resiliencia {
  component "Circuit Breaker\n(paymentCB)" as CB
  component "Bulkhead\n(paymentBH)" as BH
}

Cliente --> Gateway : HTTP (chamadas REST)

Gateway --> Pedido : Roteamento /orders/**
Gateway --> Usuario : Roteamento /users/**
Gateway --> Pagamento : Roteamento /payments/**

Pedido --> Usuario : Comunicação via Feign (IoC)
Pedido --> CB
CB --> BH
BH --> Pagamento : POST /payments/charge

note right of Pedido
  - Usa IoC com Feign para comunicação
  - Protege chamadas críticas com Circuit Breaker
  - Limita concorrência com Bulkhead
  - Possui fallback em caso de falha/latência
end note

note right of Pagamento
  - Serviço suscetível a falhas/latência
  - Chamadas protegidas por CB + Bulkhead
end note

note right of Usuario
  - Serviço simples de consulta de dados
end note
@enduml


