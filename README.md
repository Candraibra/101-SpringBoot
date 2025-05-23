# Simple Spring Boot REST API

This is a simple REST API built with Spring Boot and Kotlin. It provides basic CRUD operations for a Product entity with
standardized API responses and pagination support.

## Technologies Used

- Kotlin
- Spring Boot
- Spring Data JPA
- PostgreSQL Database

## Getting Started

### Prerequisites

- JDK 17 or higher
- Gradle
- PostgreSQL

### Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Make sure PostgreSQL is running
4. Create a database named `productdb` (if it doesn't exist):
   ```
   createdb productdb
   ```
5. Configure the database connection in `application.properties` if needed:
   ```
   spring.datasource.url=jdbc:postgresql://localhost:5432/productdb
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```
6. Run the application:
   ```
   ./gradlew bootRun
   ```
7. The API will be available at `http://localhost:8080`

## API Endpoints

### Products

| HTTP Method | Endpoint                                               | Description                         |
|-------------|--------------------------------------------------------|-------------------------------------|
| GET         | /api/products                                          | Get all products (with pagination)  |
| GET         | /api/products?page=0&size=10&sortBy=name&direction=ASC | Get paginated products with sorting |
| GET         | /api/products/{id}                                     | Get a specific product by ID        |
| POST        | /api/products                                          | Create a new product                |
| PUT         | /api/products/{id}                                     | Update an existing product          |
| DELETE      | /api/products/{id}                                     | Delete a product                    |

### Sample Product JSON

```json
{
  "name": "Product Name",
  "description": "Product Description",
  "price": 29.99
}
```

### Standardized API Response Format

All API endpoints return responses in this standardized format:

```json
{
  "status": 200,
  "success": true,
  "message": "Success message",
   "data": {
      "id": 1,
      "name": "Product Name",
      "description": "Product Description",
      "price": 29.99
   },
  "pagination": {
    "page": 0,
    "size": 10,
    "totalElements": 25,
    "totalPages": 3
  }
}
```

The `pagination` object is only included in responses from endpoints that support pagination.
