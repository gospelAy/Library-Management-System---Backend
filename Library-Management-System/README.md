# Library Management System - Backend

A RESTful API backend service for managing library books, built with Spring Boot and following clean architecture principles.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Database Schema](#database-schema)
- [Testing](#testing)
- [Configuration](#configuration)
- [Error Handling](#error-handling)

## 🎯 Overview

This is a Spring Boot application that provides a comprehensive REST API for library book management. It implements CRUD operations, search functionality, and follows industry best practices including DTOs, service layer architecture, exception handling, and comprehensive unit testing.

## ✨ Features

- ✅ **CRUD Operations**: Create, Read, Update, and Delete books
- ✅ **Search Functionality**: Search books by title or author (case-insensitive)
- ✅ **Data Validation**: Input validation for all requests
- ✅ **Exception Handling**: Global exception handling with meaningful error messages
- ✅ **DTO Pattern**: Separation of entity and API models
- ✅ **H2 In-Memory Database**: Fast development and testing
- ✅ **Unit Tests**: Comprehensive test coverage (15+ tests)
- ✅ **RESTful Design**: Following REST API best practices
- ✅ **CORS Enabled**: Support for frontend integration

## 🛠 Technology Stack

### Core Technologies
- **Java 17** - Programming language
- **Spring Boot 3.2+** - Application framework
- **Spring Data JPA** - Data persistence
- **H2 Database** - In-memory database
- **Lombok** - Boilerplate code reduction
- **Maven** - Build tool

### Testing
- **JUnit 5** - Testing framework
- **Mockito** - Mocking framework
- **AssertJ** - Fluent assertions

## 📁 Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/example/Library/Management/System/
│   │       ├── controller/
│   │       │   └── BookController.java          # REST endpoints
│   │       ├── dto/
│   │       │   ├── BookMapper.java               # DTO ↔ Entity mapper
│   │       │   ├── request/
│   │       │   │   └── BookRequestDTO.java       # Request payload
│   │       │   └── response/
│   │       │       └── BookResponseDTO.java      # Response payload
│   │       ├── exception/
│   │       │   ├── BookNotFoundException.java    # Custom exception
│   │       │   └── GlobalExceptionHandler.java   # Exception handling
│   │       ├── model/
│   │       │   └── Book.java                     # JPA Entity
│   │       ├── repository/
│   │       │   └── BookRepository.java           # Data access layer
│   │       ├── service/
│   │       │   ├── IBookService.java             # Service interface
│   │       │   └── BookService.java              # Business logic
│   │       └── LibraryManagementSystemApplication.java
│   └── resources/
│       └── application.properties                # Configuration
└── test/
    └── java/
        └── com/example/Library/Management/System/
            └── service/
                └── BookServiceTest.java          # Unit tests
```

## 🚀 Getting Started

### Prerequisites

- **Java Development Kit (JDK) 17** or higher
- **Maven 3.6+**
- **Git** (for cloning)

### Installation

1. **Clone the repository**
```bash
git clone <repository-url>
cd Library-Management-System
```

2. **Build the project**
```bash
mvn clean install
```

3. **Run the application**
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Verify Installation

Visit: `http://localhost:8080/api/books`

You should see an empty array `[]` if no books exist.

## 📖 API Documentation

### Base URL
```
http://localhost:8080/api/books
```

### Endpoints

#### 1. Create a Book
```http
POST /api/books
Content-Type: application/json

{
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "isbn": "978-0132350884",
  "publishedDate": "2008-08-01"
}
```

**Response:** `201 Created`
```json
{
  "id": 1,
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "isbn": "978-0132350884",
  "publishedDate": "2008-08-01"
}
```

#### 2. Get All Books
```http
GET /api/books
```

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "title": "Clean Code",
    "author": "Robert C. Martin",
    "isbn": "978-0132350884",
    "publishedDate": "2008-08-01"
  }
]
```

#### 3. Get Book by ID
```http
GET /api/books/{id}
```

**Response:** `200 OK` or `404 Not Found`

#### 4. Update a Book
```http
PUT /api/books/{id}
Content-Type: application/json

{
  "title": "Clean Code - Updated",
  "author": "Robert C. Martin",
  "isbn": "978-0132350884",
  "publishedDate": "2008-08-01"
}
```

**Response:** `200 OK`

#### 5. Delete a Book
```http
DELETE /api/books/{id}
```

**Response:** `200 OK`
```json
{
  "message": "deleted successfully"
}
```

#### 6. Search Books
```http
GET /api/books/search?q={query}
```

**Example:**
```
GET /api/books/search?q=clean
GET /api/books/search?q=martin
```

**Response:** `200 OK` - Returns books matching title or author

### Error Responses

#### Book Not Found
```json
{
  "timestamp": "2026-01-17T15:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Book not found with id: 999",
  "path": "/api/books/999"
}
```

## 🗄 Database Schema

### Book Entity

| Column         | Type         | Constraints                    |
|----------------|--------------|--------------------------------|
| id             | BIGINT       | PRIMARY KEY, AUTO_INCREMENT    |
| title          | VARCHAR(255) | NOT NULL                       |
| author         | VARCHAR(255) | NOT NULL                       |
| isbn           | VARCHAR(255) | NOT NULL, UNIQUE               |
| published_date | DATE         |                                |

### H2 Console Access

The H2 console is enabled for development:

**URL:** `http://localhost:8080/h2-console`

**Connection Details:**
- **JDBC URL:** `jdbc:h2:mem:librarydb`
- **Username:** `sa`
- **Password:** *(leave blank)*

## 🧪 Testing

### Run All Tests
```bash
mvn test
```

### Run Specific Test Class
```bash
mvn test -Dtest=BookServiceTest
```

### Test Coverage

The project includes comprehensive unit tests:

- ✅ **15+ unit tests** covering all service methods
- ✅ **Happy path scenarios** (successful operations)
- ✅ **Error scenarios** (exceptions, not found cases)
- ✅ **Edge cases** (empty lists, null values)
- ✅ **Data validation** (field updates, search functionality)

### Test Report
After running tests, view the report at:
```
target/surefire-reports/
```

## ⚙️ Configuration

### Application Properties

**Location:** `src/main/resources/application.properties`

```properties
# Server Configuration
server.port=8080

# H2 Database
spring.datasource.url=jdbc:h2:mem:librarydb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA Configuration
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

### Change Port
To run on a different port, modify:
```properties
server.port=9090
```

### Production Database
For production, replace H2 with PostgreSQL/MySQL:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/librarydb
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

## 🚨 Error Handling

The application implements global exception handling:

### BookNotFoundException
Thrown when a book with the specified ID doesn't exist.

**Response:** `404 Not Found`

### Validation Errors
Thrown when request data is invalid.

**Response:** `400 Bad Request`

### Internal Server Errors
Unexpected errors return `500 Internal Server Error`

## 📦 Building for Production

### Create JAR file
```bash
mvn clean package
```

### Run JAR
```bash
java -jar target/Library-Management-System-1.0.0.jar
```

### Docker Support (Optional)

**Dockerfile:**
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

**Build & Run:**
```bash
docker build -t library-management-system .
docker run -p 8080:8080 library-management-system
```

## 🔧 Development

### Code Style
- Follow Java naming conventions
- Use Lombok annotations to reduce boilerplate
- Write tests for new features
- Keep controllers thin, logic in services

### Adding New Features

1. **Create Entity** in `model/`
2. **Create Repository** in `repository/`
3. **Create DTOs** in `dto/request` and `dto/response`
4. **Create Service** interface and implementation in `service/`
5. **Create Controller** in `controller/`
6. **Write Tests** in `test/`

## 📝 API Testing with cURL

### Add a Book
```bash
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Effective Java",
    "author": "Joshua Bloch",
    "isbn": "978-0134685991",
    "publishedDate": "2017-12-27"
  }'
```

### Get All Books
```bash
curl http://localhost:8080/api/books
```

### Search Books
```bash
curl "http://localhost:8080/api/books/search?q=java"
```

### Update a Book
```bash
curl -X PUT http://localhost:8080/api/books/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Effective Java - 3rd Edition",
    "author": "Joshua Bloch",
    "isbn": "978-0134685991",
    "publishedDate": "2017-12-27"
  }'
```

### Delete a Book
```bash
curl -X DELETE http://localhost:8080/api/books/1
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is created for educational purposes as part of a software developer assessment.

## 👨‍💻 Author

Developed as part of the LawPavilion Java Software Developer recruitment exercise.

## 📞 Support

For issues or questions, please open an issue in the repository.

---

**Happy Coding! 🚀**