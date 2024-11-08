# Spring Boot Cloud Config Server

## Overview

The `springboot-cloud-config-server` microservice is a Spring Boot application that acts as a centralized configuration server. It provides configuration properties to other microservices in the system, which can be fetched from a Git repository.

## Dependencies

- `spring-cloud-config-server`: Enables the Spring Cloud Config Server functionality.
- `spring-cloud-starter-netflix-eureka-client`: Allows the config server to register with Eureka for service discovery.
- `spring-boot-starter-test`: Provides dependencies for testing.

## Configuration

### 1. `application.yml`

```yaml
spring:
    application:
        name: CONFIG-SERVER
    cloud:
        config:
            server:
                git:
                    uri: https://github.com/DharaniDJ/springboot-cloud-config-server

server:
    port: 9196

eureka:
    client:
        serviceUrl:
            defaultZone: http://localhost:8761/eureka/
        register-with-eureka: true
        fetch-registry: true
    instance:
        hostname: localhost
```

## Configuration Details

- **`spring.application.name`**: Defines the application name as `CONFIG-SERVER`.
- **`spring.cloud.config.server.git.uri`**: Specifies the URI of the Git repository where configuration files are stored.
- **`server.port`**: Sets the server port to `9196`.
- **`eureka.client`**: Configures the Eureka client settings to register and fetch the registry.
- **`eureka.instance.hostname`**: Sets the hostname for the Eureka instance.

### 2. `bootstrap.yml`

```yaml
spring:
    cloud:
        config:
            uri: http://localhost:9196
```

- **`spring.cloud.config.uri`**: Specifies the URI of the Config Server, allowing other microservices to fetch configurations from it.

## Endpoints

The `springboot-cloud-config-server` does not expose any custom endpoints. It provides configuration data to other services through its default configuration server endpoints.

## Ports

- **Config Server Port**: `9196`

