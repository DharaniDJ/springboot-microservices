# Microservices Architecture with Spring Boot

## Microservices Documentation

- [Order Service](./order-service/README.md)
- [Payment Service](./payment-service/README.md)
- [Eureka Service Registry](./service-registry/README.md)
- [Cloud API Gateway](./cloud-api-gateway/README.md)
- [Cloud Config Server](./springboot-cloud-config-server/README.md)
- [Security Service](./security-service/README.md)

## Overview
This repository hosts a microservices architecture application designed to enhance inter-service communication using REST templates and Feign clients. The application is structured around several key technologies:

- **Eureka Service Registry**: Facilitates the registration of applications for dynamic service discovery.
- **API Gateway**: Routes requests and provides a single entry point to the system, securing endpoints through OAuth2.
- **ELK Stack**: Enables centralized logging for monitoring and troubleshooting with Elasticsearch, Logstash, and Kibana.
- **Spring Cloud Config Server**: Manages shared configurations across multiple microservice environments from a central place.
- **Spring Security 6**: Implements robust security using JWT for authentication and authorization processes.

![Architecture](images/Architecture.jpg)

## Service Startup Order
Ensure the services are started in the following order for proper registration and configuration:
1. `SERVICE-REGISTRY`
2. `CONFIG-SERVER`
3. `GATEWAY-SERVICE`
4. `SECURITY-SERVICE`
5. `ORDER-SERVICE`
6. `PAYMENT-SERVICE`

## Running the ELK Stack
To run the ELK stack, perform the following steps:
1. **Start Elasticsearch**:
   ```shell
   cd C:\elastic-search\elasticsearch-8.14.3\bin
   elasticsearch.bat  # Runs on default port 9200
   ```
2. **Start Kibana**::
   ```shell
   cd C:\elastic-search\kibana-8.14.3\bin
   kibana.bat  # Interface accessible on http://localhost:5601
   ```

## Testing the Application

### 1. Register Users
- **Register user rakey**:
  ```http
  POST http://localhost:8080/auth/register
  {
      "username": "rakey",
      "password": "pwd2",
      "email": "rakey@gmail.com"
  }
  ```
- **Register user tarun**:
  ```http
  POST http://localhost:8080/auth/register
  {
    "username": "tarun",
    "password": "pwd3",
    "email": "tarun@gmail.com"
  }
  ```

### 2. Get Token for Tarun
- **Request**:
  ```http
  GET http://localhost:8080/auth/getToken
  {
      "username": "tarun",
      "password": "pwd3"
  }
- **Response**:
  ```http
  {
    "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0YXJ1biIsImlhdCI6MTcyNTU2NDE0NiwiZXhwIjoxNzI1NTY1MzQ2fQ.Vqb63DMj5t2PIcdmEuweROc4sAFZOb2KYS7323ounNY",
    "token": "60b2dcf2-dd14-4924-b8f5-d32d958c7048"
  }
  ```

### 3. Validate Token
  - **Request**:
  ```http
  GET http://localhost:8080/auth/validateToken?token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0YXJ1biIsImlhdCI6MTcyNTU2NDE0NiwiZXhwIjoxNzI1NTY1MzQ2fQ.Vqb63DMj5t2PIcdmEuweROc4sAFZOb2KYS7323ounNY
  ```

  - **Response**:
  ``` http
  {
    "message": "Token is valid"
  }
  ```
### 4. Create Book Order with Bearer Token
- **Including Bearer Token**:
  To include the Bearer Token in your request, go to the Headers tab of your API tool (like Postman), select 'Authorization', choose 'Bearer Token', and paste the JWT token received from the previous step.

- **Request**:
  ```http
  POST http://localhost:8080/order/bookOrder
  {
      "order": {
          "id": 105,
          "name": "Laptop",
          "quantity": 2,
          "price": "10000"
      },
      "payment": {}
  }
  ```
- **Response**:
  ```http
  {
    "order": {
        "id": 105,
        "name": "Laptop",
        "quantity": 2,
        "price": 10000.0
    },
    "amount": 10000.0,
    "transactionId": "501c7097-c777-47c2-b5b7-bffd371818c0",
    "message": "payment processing successful and order placed"
  }
  ```

### 5. Retrieve Payment Details with Bearer Token
- **Including Bearer Token**:
  To include the Bearer Token in your request, go to the Headers tab of your API tool (like Postman), select 'Authorization', choose 'Bearer Token', and paste the JWT token received from the previous steps.

- **Initial Request** (Expected Failure because you didn't try with Bearer token):
  ```http
  GET http://localhost:8080/payment/getPaymentByOrderId/105
  ```

- **Response**
  ```http
  {
    "timestamp": "2024-09-05T19:28:04.851+00:00",
    "path": "/payment/getPaymentByOrderId/105",
    "status": 500,
    "error": "Internal Server Error",
    "requestId": "c82eab93-25"
  }
  ```

- **Retry with Valid Token**:
  ```http
  GET http://localhost:8080/payment/getPaymentByOrderId/105
  ```

  - **Response**:
    ```http
    {
    "paymentId": 1,
    "paymentStatus": "success",
    "transactionId": "501c7097-c777-47c2-b5b7-bffd371818c0",
    "orderId": 105,
    "amount": 10000.0
    }
    ```

### 6. Refresh Token
- **Request**:
  ```http
  GET http://localhost:8080/auth/refreshToken
  {
      "token": "60b2dcf2-dd14-4924-b8f5-d32d958c7048"
  }
  ```
- **Response**:
  ```http
  {
    "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0YXJ1biIsImlhdCI6MTcyNTU2NDY2NSwiZXhwIjoxNzI1NTY1ODY1fQ.0oS9VE7lNMBAdEInGKcO_0fexsAxTmw-3I1KB3blXfg",
    "token": "60b2dcf2-dd14-4924-b8f5-d32d958c7048"
  }
  ```

## Note
Tokens must be refreshed within their valid time frames; JWT tokens are valid for 20 minutes, and refresh tokens are valid for 100 minutes. If the refresh time is exceeded, the process must restart from user registration.

## Logging
Once your services are running, you can monitor and view centralized logs in Kibana. Here's an example of how logs appear in the Kibana dashboard:

![Logging](images/6.%20Logging.jpg)

