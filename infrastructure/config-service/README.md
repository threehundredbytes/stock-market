# config-service

[Spring Cloud Config](hhttps://spring.io/projects/spring-cloud-config)
provides support for externalized configuration in a distributed system.
The configuration can be versioned under Git and can be modified at runtime.
Configuration service provides an API that microservices use to get their
configuration.

This configuration service supports both [Git](https://github.com/Dreadblade-dev/stock-market-configuration)
and [classpath](src/main/resources/services) (requires "native" profile) 
configuration.

# Encryption & Decryption

To encrypt a value, you need to make a POST request to the /encrypt endpoint and
pass that value in the request body:

    curl --location --request POST 'http://localhost:8888/encrypt' \
    --header 'Authorization: Basic YWRtaW46cGFzc3dvcmQ=' \
    --header 'Content-Type: text/plain' \
    --data-raw 'secret'

To decrypt a value, you need to make a POST request to the /decrypt endpoint and
pass that value in the request body:

    curl --location --request POST 'http://localhost:8888/decrypt' \
    --header 'Authorization: Basic YWRtaW46cGFzc3dvcmQ=' \
    --header 'Content-Type: text/plain' \
    --data-raw '15f6148bfff3400e83cf9c7c497a784d1f8734924ddd904f3285b49d6da626df'

# How to run

You can run this service with your favorite IDE like Intellij Idea or Eclipse.
Also, after you build this service, you can run it with the following command:

    ~ java -Dspring.profiles.active=native -jar infrastructure/config-service/target/config-service.jar

Optional profiles:
1. **native** - to load configuration from [classpath:/services](src/main/resources/services)
2. **elk** - to enable log shipping to Logstash (ELK-stack)