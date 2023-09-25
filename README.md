# todo_list

# Todo List Application

This is a simple todo list application developed with Spring Boot.

## Prerequisites

- Java 17
- Maven
- MySQL 

## Getting Started

1. Clone this repository to your local machine.
2. Open the project in your preferred IDE (e.g., IntelliJ IDEA).
3. Configure your database settings in `src/main/resources/application.properties`:

spring.datasource.url=jdbc:mysql://localhost:3306/todo_db
spring.datasource.username=root
spring.datasource.password=your_password


4. Run the application using Maven:

mvn spring-boot:run


5. Access the API at http://localhost:8080/api/todo/items

## Usage

- Use an API client (e.g., Postman) to interact with the API endpoints.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
