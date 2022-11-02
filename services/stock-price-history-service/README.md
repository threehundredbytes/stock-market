# stock-price-history-service

A microservice that store the stock price history.

# Endpoints

To access these endpoints you need to run the [gateway](../../infrastructure/gateway).

- Find all history prices by stock id
  ```
  curl --location --request GET 'http://localhost:8080/api/v1/stocks/1/history/'
  ```

# How to run

You can run this service with your favorite IDE like Intellij Idea or Eclipse.
Also, after you build this service, you can run it with the following command:

    ~ java -jar services/stock-price-history-service/target/stock-price-history-service.jar

Optional profiles:
1. **elk** - to enable log shipping to [Logstash](https://www.elastic.co/logstash/) ([ELK-stack](https://www.elastic.co/what-is/elk-stack))
2. **distributed-tracing** - to enable distributed tracing with
   [Spring Cloud Sleuth](https://spring.io/projects/spring-cloud-sleuth)
   and [Zipkin](https://zipkin.io/)
