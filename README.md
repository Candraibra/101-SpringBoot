# Simple Spring Boot REST API

This is a simple REST API built with Spring Boot and Kotlin. It provides basic CRUD operations for a Product entity.

## Technologies Used

- Kotlin
- Spring Boot
- Spring Data JPA
- H2 Database (in-memory)

## Getting Started

### Prerequisites

- JDK 17 or higher
- Gradle

### Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Run the application:

```
./gradlew bootRun
```

4. The API will be available at `http://localhost:8080`

## API Endpoints

### Products

| HTTP Method | Endpoint | Description |
|------------|----------|-------------|
| GET | /api/products | Get all products |
| GET | /api/products/{id} | Get a specific product by ID |
| POST | /api/products | Create a new product |
| PUT | /api/products/{id} | Update an existing product |
| DELETE | /api/products/{id} | Delete a product |

### Sample Product JSON

```json
{
  "name": "Product Name",
  "description": "Product Description",
  "price": 29.99
}
```

## H2 Console

You can access the H2 in-memory database console at `http://localhost:8080/h2-console` with the following credentials:

- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (leave empty)