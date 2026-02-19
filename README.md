# Spring Library Management

RESTful Spring Boot service for running a small library catalogue and lending workflow. The project was built for personal learning and portfolio purposes and showcases role-based access, DTO-driven APIs, and layered design.

## Features

- Manage authors and books, including filtering books by author and tracking available quantity per title.
- Register clients, delete them with optional payload echo, and keep contact details unique.
- Track borrowing orders, query them by client or date, and extend due dates in days, weeks, or months.
- Role-based security with Spring Security and JWT (stored as an HttpOnly cookie) for stateless sessions.
- Dedicated admin endpoints for user and role management (ADMIN, LIBRARIAN authorities).
- Centralized exception handling, request validation, and structured logging to `logs/log.log`.
- Unit and slice tests covering controllers, services, mappers, and exception handlers.

## Tech Stack

- Java 25 (LTS)
- Spring Boot 4.0.2 (Web, Data JPA, Validation, Security)
- Spring Framework 7 / Jakarta EE (`jakarta.*` namespaces)
- Hibernate ORM 7 & PostgreSQL
- JSON Web Tokens via `jjwt` 0.12.7
- Maven
- JUnit 5, Mockito JUnit Jupiter, Spring Test, MockMvc

## Project Layout

```
src/main/java/bg/vdrenkov/
  configuration/        # Security filter chain & password encoder
  controller/           # REST controllers for authors, books, clients, orders, users, roles
  dto/                  # Response DTO records
  entity/               # JPA entities and relationships
  exception/, handler/  # Custom exceptions + global exception handler
  jwt/                  # Token utilities, filter, and user details service
  mapper/               # Manual DTO mappers (with logging)
  repository/           # Spring Data repositories
  request/              # Validated request payloads
  service/              # Business logic layer
  util/                 # Shared constants
src/main/resources/
  application.properties
  DDL_Scripts.sql       # Optional schema bootstrap
  SpringLibraryManagement.postman_collection.json
logs/                   # Log output destination (configured in properties)
```

## Prerequisites

- Java 25
- Maven 3.9+
- PostgreSQL running locally (default config expects `localhost:7070/SpringLibraryManagement`)
- Optional: Postman for API exploration (`src/main/resources/SpringLibraryManagement.postman_collection.json`)

## Configuration

The default configuration lives in `src/main/resources/application.properties`:

- `spring.datasource.*` points to a PostgreSQL instance.
- `spring.jpa.hibernate.ddl-auto=update` lets JPA create/update tables automatically in local development.
- `jwt.secret` provides the signing key for tokens (use a strong 64+ character secret for HS512).
- `logging.file.name=logs/log.log` routes logs to the `/logs` folder.

Do not commit real credentials. Override sensitive values with environment variables or a profile-specific properties file instead of editing source directly:

```bash
set SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:7070/SpringLibraryManagement
set SPRING_DATASOURCE_USERNAME=postgres
set SPRING_DATASOURCE_PASSWORD=<your-password>
set JWT_SECRET=<64+-char-secret>
```

For production-like environments, prefer:

- `spring.jpa.hibernate.ddl-auto=validate` (or managed migrations).
- HTTPS-only cookies (`Secure`) and restrictive `SameSite`.

Use the bundled `DDL_Scripts.sql` when you prefer explicit schema management or need to recreate tables from scratch.

## Getting Started

1. Create the database (first run only):
   ```sql
   CREATE DATABASE "SpringLibraryManagement";
   ```
2. (Optional) Execute `src/main/resources/DDL_Scripts.sql` to pre-create tables in the `springlibrarymanagement` schema.
3. Build the project:
   ```bash
   mvn clean package
   ```
4. Start the service:
   ```bash
   mvn spring-boot:run
   ```
   The API listens on `http://localhost:8080`.

## Authentication & Roles

- `POST /register` self-registers a LIBRARIAN user.
- `POST /login` issues a JWT stored in an HttpOnly cookie named `Cookie`; include it on subsequent requests.
- `POST /admins/register` lets an ADMIN create additional ADMIN or LIBRARIAN accounts.
- `POST /logout` clears the JWT cookie.
- Authorities:
  - **ADMIN** – full access to user/role management and all catalogue operations (including deletions).
  - **LIBRARIAN** – CRUD access to authors, books, clients, and orders (except DELETE and admin endpoints).

When bootstrapping the system, insert an initial ADMIN user directly in the database or by seeding via SQL so you can access the admin endpoints.

## API Overview

| Method | Path                                                    | Description                                                 | Access           |
| ------ | ------------------------------------------------------- | ----------------------------------------------------------- | ---------------- |
| POST   | `/login`                                                | Authenticate an existing user and receive JWT cookie        | Public           |
| POST   | `/register`                                             | Self-register a librarian account                           | Public           |
| POST   | `/admins/register`                                      | Register a user with roles (ADMIN creates others)           | ADMIN            |
| GET    | `/authors`                                              | List authors                                                | ADMIN, LIBRARIAN |
| POST   | `/authors`                                              | Create author                                               | ADMIN, LIBRARIAN |
| GET    | `/authors/{id}`                                         | Get author details                                          | ADMIN, LIBRARIAN |
| GET    | `/books`                                                | List available books                                        | ADMIN, LIBRARIAN |
| POST   | `/books`                                                | Add book to catalogue                                       | ADMIN, LIBRARIAN |
| GET    | `/authors/{authorId}/books`                             | Books written by author                                     | ADMIN, LIBRARIAN |
| GET    | `/books/{id}`                                           | Get book details                                            | ADMIN, LIBRARIAN |
| POST   | `/clients`                                              | Register client                                             | ADMIN, LIBRARIAN |
| GET    | `/clients`                                              | List clients                                                | ADMIN, LIBRARIAN |
| GET    | `/clients/{id}`                                         | Client details                                              | ADMIN, LIBRARIAN |
| DELETE | `/clients/{id}?returnOld={bool}`                        | Delete client and optionally return removed payload         | ADMIN            |
| POST   | `/orders`                                               | Create borrowing order (checks stock & decrements quantity) | ADMIN, LIBRARIAN |
| GET    | `/orders`                                               | List orders                                                 | ADMIN, LIBRARIAN |
| GET    | `/clients/{clientId}/orders`                            | Orders placed by client                                     | ADMIN, LIBRARIAN |
| GET    | `/orders?choice={1-6}&date={yyyy-MM-dd}`                | Filter orders by issue/due date rule                        | ADMIN, LIBRARIAN |
| PUT    | `/orders/{id}?choice={1-3}&period={n}&returnOld={bool}` | Extend due date by days, weeks, or months                   | ADMIN, LIBRARIAN |
| GET    | `/users`                                                | List users                                                  | ADMIN            |
| GET    | `/users/{id}`                                           | User details                                                | ADMIN            |
| POST   | `/roles`                                                | Create role                                                 | ADMIN            |
| GET    | `/roles`                                                | List roles                                                  | ADMIN            |
| GET    | `/roles/{id}`                                           | Role details                                                | ADMIN            |

`choice` query parameters drive filter/extend behaviour:

- Orders by date: `1` issue date equals, `2` issue before, `3` issue after, `4` due equals, `5` due before, `6` due after.
- Extend due date: `1` add days, `2` add weeks, `3` add months.

Import the Postman collection for ready-made requests (`src/main/resources/SpringLibraryManagement.postman_collection.json`). Remember to capture the `Cookie` header returned by authentication calls.

## Testing

Run the automated test suite with:

```bash
mvn test
```

The suite uses JUnit 5 (Jupiter), Mockito JUnit Jupiter, and Spring’s test utilities to cover controller endpoints, service logic, DTO mappers, and exception handling.

## Logging

Application logs are written to `logs/log.log` (rotation handled by Spring Boot/Logback defaults). Adjust `logging.file.name` or log levels in `application.properties` as needed.

## Notes

This codebase is part of a private learning portfolio. Feel free to adapt it for experimentation, but it is not licensed for commercial redistribution.
