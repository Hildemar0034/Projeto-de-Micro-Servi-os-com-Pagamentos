@echo off
setlocal
echo Iniciando order-service...
mvn -q -DskipTests spring-boot:run
