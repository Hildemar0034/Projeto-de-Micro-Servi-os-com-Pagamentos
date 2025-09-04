@echo off
setlocal
echo Iniciando user-service...
mvn -q -DskipTests spring-boot:run
