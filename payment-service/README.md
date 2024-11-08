# Payment Service Microservice

## Overview

The **Payment Service** microservice is responsible for processing payments related to orders. It interacts with a database to manage payment details such as payment status, transaction ID, order ID, and amount. The service is integrated with Spring Cloud for service discovery and configuration management.

## Dependencies

The following dependencies are used in this microservice:

- **`Spring Boot Starter Data JPA`**: For database operations using JPA.
- **`Spring Boot Starter Web`**: To create RESTful web services.
- **`H2 Database`**: An in-memory database for testing and development.
- **`Lombok`**: To reduce boilerplate code using annotations.
- **`Spring Cloud Netflix Eureka Client`**: For service discovery with Eureka.
- **`Spring Cloud Config`**: For externalized configuration management.
- **`Spring Boot Starter Actuator`**: To expose endpoints for monitoring and managing the application.

## YAML Configuration

```yaml
server:
    port: 9191

spring:
    h2:
        console:
            enabled: true
    application:
        name: PAYMENT-SERVICE

logging:
    file:
        name: C:/Users/dharani.chrinta/Developer/Personal/springboot-microservices/logs/microservices.log
```

## Bootstrap Configuration

```yaml
spring:
    cloud:
        config:
            uri: http://localhost:9196
```

## Properties Configuration

```properties
spring.application.name=payment-service
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
```

## Endpoints

### POST /payment/doPayment

**Description:**  
This endpoint processes a payment and stores the payment details in the database.

**Request Body:**  
`Payment` object containing payment details.

**Response:**  
Returns the saved `Payment` object with updated payment status and transaction ID.

---

### GET /payment/getPaymentByOrderId/{orderId}

**Description:**  
This endpoint fetches payment details by order ID.

**Path Variable:**  
`orderId` - The ID of the order to retrieve payment details.

**Response:**  
Returns an `Optional<Payment>` object containing payment details.
