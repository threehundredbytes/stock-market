# payment-service

A microservice implementing a simple payment gateway.

# Endpoints

To access these endpoints you need to run the [gateway](../../infrastructure/gateway).

- Find all payments by account id
  ```
  curl --location --request GET 'http://localhost:8080/api/v1/accounts/1/payments' \
  --header 'Authorization: Bearer access-token'
  ```

- Create payment (75% success, 25% failure)
  ```
  curl --location --request POST 'http://localhost:8080/api/v1/payments' \
  --header 'Authorization: Bearer access-token' \
  --header 'Content-Type: application/json' \
  --data-raw '{ "accountId": 1, "amount": 100 }'
  ```

# How to run

You can run this service with your favorite IDE like Intellij Idea or Eclipse.
Also, after you build this service, you can run it with the following command:

    ~ java -jar services/payment-service/target/payment-service.jar

Optional profiles:
1. **elk** - to enable log shipping to [Logstash](https://www.elastic.co/logstash/) ([ELK-stack](https://www.elastic.co/what-is/elk-stack))
2. **distributed-tracing** - to enable distributed tracing with
   [Spring Cloud Sleuth](https://spring.io/projects/spring-cloud-sleuth)
   and [Zipkin](https://zipkin.io/)
