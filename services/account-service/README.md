# account-service

A microservice responsible for managing user accounts.

# Endpoints

To access these endpoints you need to run the [gateway](../../infrastructure/gateway).

- Find all accounts by user:
  ```
  curl --location --request GET 'http://localhost:8080/api/v1/accounts' \
  --header 'Authorization: Bearer access-token'
  ```

- Find a specific account by id:
  ```
  curl --location --request GET 'http://localhost:8080/api/v1/accounts/1' \
  --header 'Authorization: Bearer access-token'
  ```

- Find all stocks by account id:
  ```
  curl --location --request GET 'http://localhost:8080/api/v1/accounts/1/stocks' \
  --header 'Authorization: Bearer access-token'
  ```

- Create account:
  ```
  curl --location --request POST 'http://localhost:8080/api/v1/accounts' \
  --header 'Authorization: Bearer access-token'
  ```

# How to run

You can run this service with your favorite IDE like Intellij Idea or Eclipse.
Also, after you build this service, you can run it with the following command:

    ~ java -jar services/account-service/target/account-service.jar

Optional profiles:
1. **elk** - to enable log shipping to [Logstash](https://www.elastic.co/logstash/) ([ELK-stack](https://www.elastic.co/what-is/elk-stack))
2. **distributed-tracing** - to enable distributed tracing with
   [Spring Cloud Sleuth](https://spring.io/projects/spring-cloud-sleuth)
   and [Zipkin](https://zipkin.io/)