# School Management System

A Spring Boot REST API for managing students, courses, and lectures. This project includes endpoints to retrieve students, courses, and lectures, along with student enrollment.

## Table of Contents
- [Features](#features)
- [Technologies](#technologies)
- [Design Decisions](#design-decisions)
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

### Database
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

#### Get a Course with Student Count

**Request:**
```http
GET /api/v1/courses
```

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
