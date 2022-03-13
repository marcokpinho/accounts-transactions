# accounts-transactions

## Execução

É possível executar a aplicação de duas maneiras. Basta abrir o terminal na raíz do projeto e executar um dos comandos:
* `./gradlew clean build bootRun`: Irá executar a aplicação via gradle, utilizando um banco de dados em memória (H2).
* `docker-compose up`: Irá subir container para um banco de dados postgres e outro container para a aplicação.


## Endpoints

Segue algumas curls para  ajudar nos testes:

### Criação de contas
```
curl -X POST \
  http://localhost:8080/accounts \
  -H 'Content-Type: application/json' \
  -d '{
    "document_number": "1111111111"
}'
```

### Busca de contas por id
```
curl -X GET http://localhost:8080/accounts/1
```

### Criação de transações
```
curl -X POST \
  http://localhost:8080/transactions \
  -H 'Content-Type: application/json' \
  -d '{
    "account_id": 1,
    "operation_type_id": 4,
    "amount": 50.45
}'
```
