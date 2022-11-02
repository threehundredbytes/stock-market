# discovery-service

[Spring Cloud Netflix Eureka Server](https://spring.io/projects/spring-cloud-netflix)
implements a Service Discovery pattern. This service provides a REST API for
the service registry.

# How to run

You can run this service with your favorite IDE like Intellij Idea or Eclipse.
Also, after you build this service, you can run it with the following command:

    ~ java -jar infrastructure/discovery-service/target/discovery-service.jar

Optional profiles:
1. **elk** - to enable log shipping to Logstash (ELK-stack)