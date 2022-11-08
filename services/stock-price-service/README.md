# stock-price-service

A microservice that randomly changes stock prices.

It does not provide any endpoints.

# How to run

You can run this service with your favorite IDE like Intellij Idea or Eclipse.
Also, after you build this service, you can run it with the following command:

    ~ java -jar services/stock-price-service/target/stock-price-service.jar

Optional profiles:
1. **elk** - to enable log shipping to [Logstash](https://www.elastic.co/logstash/) ([ELK-stack](https://www.elastic.co/what-is/elk-stack))
2. **distributed-tracing** - to enable distributed tracing with
   [Spring Cloud Sleuth](https://spring.io/projects/spring-cloud-sleuth)
   and [Zipkin](https://zipkin.io/)
