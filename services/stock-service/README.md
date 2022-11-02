# stock-service

A microservice responsible for creating new stocks and receiving existing ones.

# Endpoints

To access these endpoints you need to run the [gateway](../../infrastructure/gateway).

- Find all stocks
  ```
  curl --location --request GET 'http://localhost:8080/api/v1/stocks/1/history/'
  ```
  
- Find all stocks by ids
  ```
  curl --location --request GET 'http://localhost:8080/api/v1/stocks/1,2,3'
  ```

- Create stock (**business** role is required)
  ```
  curl --location --request POST 'http://localhost:8080/api/v1/stocks' \
  --header 'Authorization: Bearer access-token' \
  --header 'Content-Type: application/json' \
  --data-raw '{
    "name": "My company LTD",
    "ticker": "MYCO",
    "price": 5
  }'
  ```

# How to run

You can run this service with your favorite IDE like Intellij Idea or Eclipse.
Also, after you build this service, you can run it with the following command:

    ~ java -jar services/stock-service/target/stock-service.jar

Optional profiles:
1. **elk** - to enable log shipping to [Logstash](https://www.elastic.co/logstash/) ([ELK-stack](https://www.elastic.co/what-is/elk-stack))
2. **distributed-tracing** - to enable distributed tracing with
   [Spring Cloud Sleuth](https://spring.io/projects/spring-cloud-sleuth)
   and [Zipkin](https://zipkin.io/)
