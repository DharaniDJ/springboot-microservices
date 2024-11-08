# Service Registry Microservice

## Overview
The **Service Registry** microservice is responsible for maintaining a central registry of microservices within a distributed system. It uses Netflix Eureka to allow microservices to register themselves and discover other registered services, enabling dynamic scaling and resilience in the architecture.

This microservice acts as a Eureka Server, which manages service instances and provides information to clients about the available services.

## Dependencies

The following dependencies are required to build and run the Service Registry microservice:

```gradle
dependencies {
	implementation 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-server'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
}
```

## Configuration

1. `application.yml`

This file contains the essential configuration for the Eureka Server, including the port and Eureka-specific properties:

```yaml
eureka:
  client:
    register-with-eureka: false
    fetch-registry: false

server:
  port: 8761
```

2. `bootstrap.yml`

This file is used for externalized configuration and integrates the service registry with a Spring Cloud Config server:

```yaml
spring:
    cloud:
        config:
            uri: http://localhost:9196
```

## Main Application Class

The main application class initializes the Eureka Server. The `@EnableEurekaServer` annotation is used to activate the Eureka Server functionality.

```java
@SpringBootApplication
@EnableEurekaServer
public class ServiceRegistryApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceRegistryApplication.class, args);
    }
}
```