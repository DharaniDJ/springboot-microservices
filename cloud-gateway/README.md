# Cloud API Gateway Microservice

## Overview

The Cloud API Gateway microservice serves as a gateway to route requests to various backend microservices. It ensures that only authenticated requests pass through, validates JWT tokens, and provides circuit-breaking functionality using Hystrix. The gateway is also integrated with Eureka for service discovery.

## Dependencies

- **Spring Boot 3.3.2**
- **Spring Cloud Netflix Eureka Client**
- **Spring Cloud Gateway**
- **Spring Cloud Netflix Hystrix**
- **Spring Cloud Config Client**
- **Spring Boot Actuator**
- **JJWT (Java JWT)**

## Configuration

### 1. `application.yml`

This configuration file defines the routes for the API Gateway, specifying the target URIs for each route and applying the `AuthenticationFilter` to secure endpoints. It also configures server port and management endpoints.

```yaml
spring:
    application:
        name: GATEWAY-SERVICE
    cloud:
        gateway:
            routes:
                - id: order-service
                  uri: lb://ORDER-SERVICE
                  predicates:
                    - Path=/order/**
                  filters:
                    - AuthenticationFilter 
                  # filters:
                  #   - name: CircuitBreaker
                  #     args:
                  #       name: order-service
                  #       fallbackUri: forward:/orderFallBack
                - id: payment-service
                  uri: lb://PAYMENT-SERVICE
                  predicates:
                    - Path=/payment/**
                  filters:
                    - AuthenticationFilter 
                  # filters:
                  #   - name: CircuitBreaker
                  #     args:
                  #       name: payment-service
                  #       fallbackUri: forward:/paymentFallBack
                - id: security-service
                  uri: lb://SECURITY-SERVICE
                  predicates:
                    - Path=/auth/**
                  # filters:
                  #   - name: CircuitBreaker
                  #     args:
                  #       name: payment-service
                  #       fallbackUri: forward:/paymentFallBack

server:
    port: 8989

#Expose all actuator endpoints
management:
  endpoints:
    web:
      exposure:
        include: "*"

hystrix:
  command:
    fallbackcmd:
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 5000

```

### 2. `bootstrap.yml`

This file sets up the connection to the Spring Cloud Config server, specifying the URI where the configuration properties are fetched from.

```yaml
spring:
    cloud:
        config:
            uri: http://localhost:9196
```

## Components

### Authentication Filter

The `AuthenticationFilter` ensures that all secured routes are accessed only by authenticated users. It checks for the presence of a JWT token in the `Authorization` header and validates it.

### Route Validator

The `RouteValidator` defines a list of open API endpoints that do not require authentication. All other routes are secured and must pass through the authentication filter.

### JWT Utility

The `JwtUtil` class handles JWT token parsing and validation using a secret key.

## Endpoints

- **/order/**: Routes to the Order Service.
- **/payment/**: Routes to the Payment Service.
- **/auth/**: Routes to the Security Service.

## Ports

- **Gateway Service Port**: `8989`