## Orders App
### Overview

This is a Spring Boot application that uses a PostgreSQL database. The database is started using Docker Compose, and must be running before the application starts.

### Prerequisites

* Java 17
* Maven
* Docker & Docker Compose installed

### Setup

### 1. Build and Run the Application

* Using Maven:

```
mvn clean install
mvn spring-boot:run
```

* Using IDEA without applied profiles.

### 4. Verify

Application logs should show a successful database connection.

Access the REST API via http://localhost:8081.

Access the Swagger UI via http://localhost:8081/swagger-ui/index.html

