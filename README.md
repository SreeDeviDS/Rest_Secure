# Job Portal REST API

A secured REST API for managing job postings, built with Spring Boot. It supports full CRUD, keyword and experience search, user registration, and role-based access (USER / ADMIN) using Spring Security with HTTP Basic authentication.

## Tech stack

- Java 25, Maven
- Spring Boot 4.1.1 (Web MVC)
- Spring Data JPA / Hibernate
- MySQL
- Spring Security (HTTP Basic, BCrypt, stateless sessions)
- Bean Validation, Lombok

## Features

- Full CRUD for job posts
- Search by keyword (matches profile or description) and by maximum required experience
- User registration with BCrypt-hashed passwords
- Role-based access: any logged-in user can read; only ADMIN can create, update, delete, or bulk-load
- Request validation with clear error messages
- Global exception handling (400, 401, 403, 404 responses)
- Layered structure: Controller → Service → Repository

## Project structure

```
com.JobAppRest.Rest
├── JobController          REST endpoints
├── GlobalExceptionHandler  400 / 404 error responses
├── Config/SecurityConfig   authentication, authorization, CORS
├── service/                JobService, UserService, UserPrincipal
├── repo/                   JobRepo, UserRepo
└── model/                  JobPost, User, Role
```

## Getting started

**Prerequisites:** JDK 25, Maven (or the included `mvnw`), and MySQL running locally.

1. Create the database:
   ```
   CREATE DATABASE companydb;
   ```
2. Set your MySQL password in an environment variable named `DB_PASSWORD`. The application reads it through `spring.datasource.password=${DB_PASSWORD}`. In IntelliJ: Run → Edit Configurations → Modify options → Environment variables. From a Windows terminal, run `set DB_PASSWORD=your-password` before starting the app. If your MySQL username or database name differ, change them in `src/main/resources/application.properties`. Never commit real credentials.
3. Run the application:
   ```
   ./mvnw spring-boot:run
   ```
4. The API starts at `http://localhost:8080`. Tables are created automatically on first run.

### Creating an ADMIN user

`POST /register` always creates a user with the USER role, whatever role is sent in the request. To test the ADMIN-only endpoints, register a user and then change that user's `role` to `ADMIN` directly in the MySQL user table.

## Test console

`frontend/jobpost-console.html` is a simple browser page for trying the API. You can register, log in with a username and password (sent as HTTP Basic authentication), and create, view, update, delete, and search job posts. To use it, start the Spring Boot application, open the HTML file in a browser, and keep the base URL as `http://localhost:8080`. New users registered through the page always get the USER role.

## Authentication

All endpoints except `/register` require HTTP Basic authentication (username and password). Sessions are stateless, so send credentials with every request.

## API endpoints

Base URL: `http://localhost:8080`

| # | Method | URL | Access | Request body | Success response |
|---|--------|-----|--------|--------------|------------------|
| 1 | POST | `/register` | Public | username, password | 201, created user (id, username, role) |
| 2 | GET | `/JobPosts` | Logged-in user | None | 200, list of all job posts |
| 3 | GET | `/JobPost/{id}` | Logged-in user | None | 200, the job post |
| 4 | GET | `/JobPost/keyword/{keyword}` | Logged-in user | None | 200, posts whose profile or description contains the keyword |
| 5 | GET | `/JobPost/experience/{exp}` | Logged-in user | None | 200, posts requiring experience less than or equal to `exp` |
| 6 | POST | `/JobPost` | ADMIN | JobPost JSON | 201, the created post |
| 7 | PUT | `/JobPost/{id}` | ADMIN | JobPost JSON | 200, the updated post |
| 8 | DELETE | `/JobPost/{id}` | ADMIN | None | 200, "JobPost deleted successfully" |
| 9 | POST | `/JobPost/load` | ADMIN | None | 200, "All Rows Inserted" |

URLs are case-sensitive.

`POST /JobPost/load` is a development helper that inserts five sample job posts so the API has data to work with. Each call adds five more rows, so call it once.

### Sample JobPost body (endpoints 6 and 7)

```json
{
  "postProfile": "Software Engineer",
  "postDesc": "Exciting opportunity for a skilled software engineer.",
  "reqExperience": 3,
  "postTechStack": ["Java", "Spring", "SQL"]
}
```

### Validation rules

- `postProfile` and `postDesc` are required
- `reqExperience` cannot be negative
- `postTechStack` must contain 1 to 10 items

### Sample register body (endpoint 1)

```json
{
  "username": "alice",
  "password": "your-password"
}
```

## Error responses

| Status | When | Body |
|--------|------|------|
| 400 | Validation failed | JSON with timestamp, status, error, message |
| 401 | Missing or wrong credentials | "Invalid username or password" |
| 403 | A USER calls an ADMIN-only endpoint | "You don't have permission to perform this action" |
| 404 | Job post id not found (endpoints 3, 7, 8) | JSON with a message such as "JobPost with id 5 not found" |

## Testing

Endpoints were tested manually with Postman: CRUD, search, registration, and authentication, including the 401 and 403 cases.

## Known limitations

- The password field is not validated, and duplicate usernames are not checked.
- CORS currently allows all origins, which is suitable for local testing only.
- Entities are used directly as request and response bodies (no DTOs).
- No automated unit or integration tests yet.

## Possible improvements

- JWT bearer-token authentication instead of HTTP Basic
- DTOs for requests and responses
- Unit tests with JUnit and Mockito
- Docker setup
