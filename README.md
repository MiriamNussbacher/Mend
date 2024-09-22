# Repository Scanning Web Application

## Overview

This repository contains a web application designed to perform automated scans on code repositories. The application is built with **scalability**, **reliability**, and **performance** in mind. It provides an API for initiating scans, retrieving results, and handling multiple scan types. The application is designed to handle large-scale operations by leveraging asynchronous processing and message queuing.

---

## Technologies

The following technologies are used in this project:

- **Spring Boot**: Provides the framework for building the RESTful APIs and managing the application lifecycle.
- **Hibernate**: Manages the interaction between Java objects and the database, allowing for simplified data persistence. The **Database-First approach** was used.
- **RabbitMQ**: Enables asynchronous message queuing for handling scan requests and processing them in the background.
- **JUnit & Mockito**: Used for testing the application, ensuring the integrity of the codebase with unit and integration tests.
- **MySQL**: The relational database used for storing scan data, types, and other related information.

---

## Architecture

The architecture of the application follows a layered approach:

- **Controller Layer**: Handles incoming API requests, applies validation, and delegates processing to the service layer.
- **Validation Layer**: Ensures that all incoming requests are properly validated, returning clear and concise error messages for invalid inputs.
- **Service Layer**: Implements the core business logic, such as initiating and processing scans, managing the database, and communicating with RabbitMQ for message queuing.
- **Repository Layer**: Uses Hibernate to manage interactions with the MySQL database, handling all data-related operations.

### Key architectural features include:

- **Asynchronous Processing**: Long-running tasks, such as scans, are processed asynchronously using RabbitMQ and Spring's `@Async` support.
- **Scalable Design**: The system is designed to handle a high volume of scans by queuing tasks and processing them in parallel, improving throughput and efficiency.
- **Database-Driven Configuration**: Scan types, User Types are stored in the database, allowing new scan types to be added without modifying the codebase. 
- Scan Statuses are not meant to graw that much so they are saved as an Enum.
- 

---

## How to Run the Application

### Prerequisites

- **Docker** and **Docker Compose** are required to run the application in a containerized environment.
- Ensure that **Maven** is installed for building the application.

### Steps to Run

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/MiriamNussbacher/Mend.git
   cd scanning-app
Build the Application: Ensure you have Maven installed, then build the project:


mvn clean package -DskipTests


Run the Application with Docker Compose
All services, including MySQL and RabbitMQ, are set up via Docker Compose:

docker-compose up --build

## Access the Application ##
The application will be available at:
http://localhost:8080

You can explore the API endpoints using the Swagger UI at:
http://localhost:8080/swagger-ui.html

**Key Features**
1. Scan Types Management
   The available scan types (e.g., RENOVATE, SCA, SAST) are stored in the database. If a new scan type is required, it can be added directly to the database without changing the code. This approach allows for flexibility and easy extensibility.

2. Asynchronous Processing via RabbitMQ
   Scans are processed asynchronously using RabbitMQ, ensuring that the system can handle multiple requests simultaneously without blocking the main thread. The application supports both synchronous and asynchronous processing. For larger workloads, RabbitMQ queues the scans, processes them in the background, and updates the status once completed.

3. Validation
   All inputs are validated before being processed. This ensures that only valid data enters the system, improving reliability and providing helpful feedback in case of errors. For example, if an invalid scan type or commit ID is provided, the API will respond with an appropriate 400 Bad Request error message.

4. Performance Optimizations
   Indexes have been added to key database fields to ensure efficient querying, especially when dealing with large datasets.
   The use of asynchronous processing (via Java’s CompletableFuture and @Async) ensures that long-running operations, such as scanning, do not block other processes, improving the system’s overall performance and scalability.
5. Logging
   The application logs all significant actions, such as scan initiation, processing, and completion, using INFO level logging. Error conditions are logged at the ERROR level, providing clear diagnostic information when issues arise.
6. Logs are stored in a file and all settings can easaly be configured.
7. All HTTP Request and responses are logged using a **HandlerInterceptor**


## Environment Variable for Response Handling ##
Using RabbitMq is the best solution for many reasons. but it requires having a client that supports SSE (a browser)
This application uses an environment variable to control whether scan results are processed asynchronously with RabbitMQ and SSE
or synchronously with a direct HTTP response.

**app.use-async-processing:**
This environment variable determines whether scan requests are processed asynchronously or synchronously.
Value:
true: The application will process scans asynchronously, utilizing RabbitMQ and sending real-time updates via Server-Sent Events (SSE).
To do that you can add a scan and access the http://localhost:8080/api/scan-status/{scanId}/sse url in the browser. when time comes the message will appear.
false: The application will handle scans synchronously, returning the result directly in the HTTP response.
