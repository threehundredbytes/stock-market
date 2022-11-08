# gateway

This gateway is implemented with [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway)
This gateway uses the Circuit Breaker pattern to call the services. 
The Circuit Breaker pattern is implemented using 
[Spring Cloud Circuit Breaker](https://spring.io/projects/spring-cloud-circuitbreaker)
And [Resilience4j](https://resilience4j.readme.io/docs)

# How to run

You can run this service with your favorite IDE like Intellij Idea or Eclipse.
Also, after you build this service, you can run it with the following command:

    ~ java -jar infrastructure/gateway/target/gateway.jar

Optional profiles:
1. **elk** - to enable log shipping to [Logstash](https://www.elastic.co/logstash/) ([ELK-stack](https://www.elastic.co/what-is/elk-stack))
2. **distributed-tracing** - to enable distributed tracing with
   [Spring Cloud Sleuth](https://spring.io/projects/spring-cloud-sleuth)
   and [Zipkin](https://zipkin.io/)