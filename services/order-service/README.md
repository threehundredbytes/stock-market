# order-service

A microservice responsible for orders processing.

# Endpoints

To access these endpoints you need to run the [gateway](../../infrastructure/gateway).

- Find all orders by account id
  ```
  curl --location --request GET 'http://localhost:8080/api/v1/accounts/1/orders' \
  --header 'Authorization: Bearer access-token'
  ```
- Find all orders by stock id
  ```
  curl --location --request GET 'http://localhost:8080/api/v1/orders/stocks/1' \
  --header 'Authorization: Bearer access-token'
  ```
- Create order
  ```
  curl --location --request POST 'http://localhost:8080/api/v1/orders' \
  --header 'Authorization: Bearer access-token' \
  --header 'Content-Type: application/json' \
  --data-raw '{ 
    "stockId": 1,
    "accountId": 1,
    "pricePerStock": 120,
    "quantity": 3,
    "orderType": "PURCHASE"
  }'
  ```

# How to run

You can run this service with your favorite IDE like Intellij Idea or Eclipse.
Also, after you build this service, you can run it with the following command:

    ~ java -Dspring.profiles.active=simulate-trading -jar services/order-service/target/order-service.jar

Optional profiles:
1. **simulate-trading** - to enable trading simulation (stock-market will
   automatically close all remaining orders, even if there are no opposite orders)
2. **elk** - to enable log shipping to [Logstash](https://www.elastic.co/logstash/) ([ELK-stack](https://www.elastic.co/what-is/elk-stack))
3. **distributed-tracing** - to enable distributed tracing with
   [Spring Cloud Sleuth](https://spring.io/projects/spring-cloud-sleuth)
   and [Zipkin](https://zipkin.io/)
