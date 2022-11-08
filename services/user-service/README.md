# stock-service

A microservice responsible for creating users with specific roles via Keycloak API.

# Endpoints

To access these endpoints you need to run the [gateway](../../infrastructure/gateway).

- Create user with role (**CLIENT** or **BUSINESS**)
  ```
  curl --location --request POST 'http://localhost:8080/api/v1/auth/signup' \
  --header 'Content-Type: application/json' \
  --data-raw '{
    "username": "user",
    "password": "password",
    "role": "BUSINESS"
  }'
  ```

# How to run

You can run this service with your favorite IDE like Intellij Idea or Eclipse.
Also, after you build this service, you can run it with the following command:

    ~ java -jar services/user-service/target/user-service.jar

Optional profiles:
1. **elk** - to enable log shipping to [Logstash](https://www.elastic.co/logstash/) ([ELK-stack](https://www.elastic.co/what-is/elk-stack))
2. **distributed-tracing** - to enable distributed tracing with
   [Spring Cloud Sleuth](https://spring.io/projects/spring-cloud-sleuth)
   and [Zipkin](https://zipkin.io/)
