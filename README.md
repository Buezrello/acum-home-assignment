# School Management System

A Spring Boot REST API for managing students, courses, and lectures. This project includes endpoints to retrieve students, courses, and lectures, along with student enrollment.

## Table of Contents
- [Features](#features)
- [Technologies](#technologies)
- [Design Decisions](#design-decisions)
- [API Documentation](#api-documentation)
- [Getting Started](#getting-started)
- [Database](#database)
- [Endpoints](#endpoints)
- [Running Tests](#running-tests)
- [Project Structure](#project-structure)

## Features

- **Student Management**: Add, edit, and retrieve student details.
- **Reports**: Retrieve courses with student counts and lectures.

## Technologies

- **Java 17**
- **Spring Boot 3.x**
- **Hibernate/JPA**
- **H2 Database** (in-memory for easy setup)
- **Maven**
- **JUnit 5** and **Mockito** for unit testing

## Design Decisions

### Use of Native Queries

In this project, native SQL queries are used in certain cases instead of standard JPA queries for the following reasons:

1. **Complex Joins and Aggregations**: Some queries, such as those that retrieve courses with student counts and associated lectures, involve complex joins and aggregations. Native queries allow for more control and flexibility in constructing these types of queries, ensuring optimal performance.
2. **Performance Optimization**: Native queries often provide better performance for data retrieval, particularly when working with large datasets or complex relationships.
3. **Precise SQL Control**: Using native queries allows us to leverage SQL-specific features and optimizations, which are sometimes difficult or verbose to achieve with standard JPA query methods.

While JPA is used for basic CRUD operations and simpler queries, native SQL queries help handle more advanced data retrieval requirements efficiently.

## API Documentation

This project uses **Swagger UI** (powered by Springdoc OpenAPI) for API documentation. Once the application is running, you can access the Swagger UI at:

- **Swagger UI**: `http://localhost:8080/api/v1/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/api/v1/v3/api-docs`

Swagger UI provides an interactive interface for exploring and testing the available API endpoints.


## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.8 or higher

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/Buezrello/acum-home-assignment.git
   cd school-management-system
   ```
2. Install dependencies and package the application:
   ```bash
   mvn clean install
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
   The API will be available at `http://localhost:8080`.

## Database
The application uses an in-memory H2 database by default. You can access the H2 console at `http://localhost:8080/api/v1/h2-console` with the following credentials:
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: _(leave blank)_

Data is initialized with `data.sql` to provide sample records for students, courses, and lectures.

## Endpoints

| HTTP Method | Endpoint                        | Description                                                  |
|-------------|---------------------------------|--------------------------------------------------------------|
| `GET`       | `/api/v1/students/{id}`         | Get a student by ID, including enrolled courses.             |
| `POST`      | `/api/v1/students`              | Add a new student.                                           |
| `PUT`       | `/api/v1/students/{id}`         | Update an existing student.                                  |
| `GET`       | `/api/v1/students`              | Get a list of all students.                                  |
| `GET`       | `/api/v1/courses`               | Get a list of courses with student counts and list lectures. |

### Sample Requests

#### Add a Student

**Request:**
```http
POST /api/v1/students
```

**Body:**
```json
{
    "firstName": "Alice",
    "lastName": "Johnson",
    "email": "alice.johnson@example.com",
    "fieldOfStudy": "Computer Science",
    "courseIds": [1, 2]
}
```

#### Get All Courses with Students Count

**Request:**
```http
GET /api/v1/courses
```

**Example:**
```http
GET /api/v1/courses/1
```

#### Get a Student by ID (with Enrolled Courses)

**Request:**
```http
GET /api/v1/students/{id}
```

**Example:**
```http
GET /api/v1/students/1
```

#### Update a Student (Partial Update)

**Request:**
```http
PUT /api/v1/students/{id}
```

**Body (Partial Update Example):**
```json
{
    "firstName": "John Updated",
    "email": "john.updated@example.com",
    "courseIds": [1, 3]
}
```

In this case, only `firstName`, `email`, and `courseIds` will be updated. Fields that are not provided in the request (e.g., `lastName` and `fieldOfStudy`) will remain unchanged.

#### Get All Students

**Request:**
```http
GET /api/v1/students
```

### Additional Notes
- **Course Validation**: When updating a student's courses, the provided `courseIds` must be valid (i.e., they must refer to existing courses).
- **Partial Updates**: For the `PUT` request, only the fields provided in the request body will be updated, while others will remain unchanged.


## Running Tests

Unit tests for the application are available and can be run with the following command:
```bash
mvn test
```

## Project Structure

```plaintext
src
├── main
│   ├── java/com/acum/schoolmanagement
│   │   ├── controller     # REST Controllers
│   │   ├── dto            # Data Transfer Objects (DTOs)
│   │   ├── entity         # JPA Entities
│   │   ├── repository     # Spring Data JPA Repositories
│   │   └── service        # Business Logic Services
│   └── resources
│       ├── application.yml # Spring Boot Configuration
│       └── data.sql       # Sample Data Initialization
│       └── schema.sql       # Code for Tables Generation     
└── test
    └── java/com/acum/schoolmanagement   # Unit Tests sample
```
