@echo off
setlocal
echo Iniciando payment-service...
mvn -q -DskipTests spring-boot:run
