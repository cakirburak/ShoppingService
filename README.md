# ShoppingServices

ShoppingServices is a microservices-based ecosystem developed using Maven and Spring Boot. It consists of several services designed to handle different aspects of the shopping experience, including product management, order processing, inventory management, and notifications.

## Architecture Overview

[Architectural Design Diagram](https://black-cyb-52.tiiny.site/)

The project follows a microservices architecture, where each service is responsible for a specific domain. The services communicate with each other through REST APIs and messaging queues. Key components of the architecture include:

- **API Gateway**: Implemented using Spring Cloud Gateway, serves as the entry point for client requests and handles routing, filtering, and security.
- **Service Discovery**: Utilizes Spring Eureka for service discovery and registration, enabling dynamic communication between services.
- **Security**: Integrated Keycloak for authentication and authorization, ensuring secure access to APIs.
- **Database**: MongoDB is used for the product service, while MySQL is used for the order and inventory services.
- **Messaging**: Kafka is employed for asynchronous communication between services, enabling event-driven architecture.
- **Circuit Breaker**: Implemented circuit breaker patterns for the order service to handle request timeouts and retries, improving system resilience.

## Technologies Used

- Spring Boot 3
- Maven
- Spring Cloud Gateway
- Spring Eureka
- Keycloak
- MongoDB
- MySQL
- Kafka
- Resilience4j (for circuit breaker)

## Services

1. **product-service**: Manages product information and interacts with the MongoDB database.
2. **order-service**: Handles order processing and interacts with the MySQL database. Implements circuit breaker patterns.
3. **inventory-service**: Manages inventory levels and interacts with the MySQL database.
4. **notification-service**: Consumes events from the order service via Kafka and sends notifications to users.

## Usage

Once the services are running, you can interact with the APIs using tools like Postman or curl. Refer to the [API documentation](https://woolly-society-a2b.notion.site/ShoppingServices-API-Document-c8a3c5ad71034cc590ad25ca2237904d) for detailed endpoints and request/response formats.

## Contributing

Contributions are welcome! If you'd like to contribute to the project, please follow these guidelines:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Make your changes and ensure they are well-tested.
4. Commit your changes with descriptive commit messages.
5. Push your changes to your fork.
6. Submit a pull request to the main repository.

## License

This project is licensed under the [MIT License](LICENSE).
