# notification-service

A microservice responsible for user notifications such as order status changes,
stock prices, and creating custom stock price notifications.

# Endpoints

To access these endpoints you need to run the [gateway](../../infrastructure/gateway).

- WebSocket endpoint: `http://localhost:8080/connect`

  Topics to subscribe:
  - `/topic/notifications/orders/closed` - closed orders
  - `/topic/notifications/orders/confirmed` - confirmed orders
  - `/topic/notifications/orders/rejected` - rejected orders
  - `/topic/stocks/{stockId}/prices` - stock price changes by id
  - `/user/topic/notifications/stocks/prices` - stock prices notifications

- Find all notifications by user:
  ```
  curl --location --request GET 'http://localhost:8080/api/v1/notifications' \
  --header 'Authorization: Bearer access-token'
  ```
  
- Create notification:
  ```
  curl --location --request POST 'http://localhost:8080/api/v1/notifications' \
  --header 'Authorization: Bearer access-token' \
  --header 'Content-Type: application/json' \
  --data-raw '{ "stockId": 1, "atPrice": 120 }'
  ```

- Deactivate notification by id
  ```
  curl --location --request DELETE 'http://localhost:8080/api/v1/notifications/1' \
  --header 'Authorization: Bearer access-token'
  ```

# How to run

You can run this service with your favorite IDE like Intellij Idea or Eclipse.
Also, after you build this service, you can run it with the following command:

    ~ java -jar services/notification-service/target/notification-service.jar

Optional profiles:
1. **elk** - to enable log shipping to [Logstash](https://www.elastic.co/logstash/) ([ELK-stack](https://www.elastic.co/what-is/elk-stack))
2. **distributed-tracing** - to enable distributed tracing with
   [Spring Cloud Sleuth](https://spring.io/projects/spring-cloud-sleuth)
   and [Zipkin](https://zipkin.io/)

