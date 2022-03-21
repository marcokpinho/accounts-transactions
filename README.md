# accounts-transactions

## Requimentos

* Java 11 configurado
* Gradle 6.8+
 ---
## Execução

Para subir a aplicação, basta abrir o terminal na raíz do projeto e seguir os seguintes passos:

1) `gradle build`
2) Escolhe uma das opções
    1) Subir a aplicação via gradle com banco de dados em memória (H2): `gradle bootRun`
    2) Subir a aplicação e um banco de dados Postgres via docker: `docker-compose up`

---
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
---
## Swagger

Também é possível realizar os testes dos endpoints pelo swagger, acessando o link http://localhost:8080/swagger-ui.html