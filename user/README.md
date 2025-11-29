# FIPE User Service

This project is a microservice for user management in the FIPE system. It follows hexagonal architecture (ports and adapters) and uses Quarkus, the Supersonic Subatomic Java Framework.

## Architecture

The project follows a clean hexagonal architecture with the following layers:

- **Domain Layer** (`domain/`): Contains business logic, models, enums, exceptions, and port interfaces
  - `model/`: Domain models (User)
  - `enums/`: Domain enumerations (Role)
  - `exception/`: Custom exceptions
  - `port/in/usecase/`: Input port interfaces (use cases)
  - `port/out/`: Output port interfaces (repository)

- **Application Layer** (`application/`): Contains use case implementations
  - `usecase/`: Business logic implementations

- **Infrastructure Layer** (`infrastructure/`): Contains adapters for external integrations
  - `adapter/in/rest/`: REST API controllers and DTOs
  - `adapter/out/persistence/`: Database adapters (JPA entities and repositories)
  - `security/`: Security utilities (password encoding)

## Features

- User CRUD operations
- Password management
- Role-based access control (ADMIN, USER, MANAGER, VIEWER)
- JWT authentication support
- RESTful API with OpenAPI/Swagger documentation
- PostgreSQL database with Liquibase migrations
- Comprehensive error handling

## Prerequisites

- JDK 17 or later
- Maven 3.9+
- PostgreSQL 12+
- Docker (optional)

## Configuration

The application can be configured via `src/main/resources/application.yml`:

- HTTP port: 8081
- Database: PostgreSQL on localhost:5432 (database: fipe_user)
- Default credentials: fipe/fipe

## Running the application

### Development mode

You can run your application in dev mode that enables live coding using:

```shell
./mvnw quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8081/q/dev/>.

### Accessing the API

- API Base URL: `http://localhost:8081/api/users`
- Swagger UI: `http://localhost:8081/swagger-ui`
- OpenAPI Spec: `http://localhost:8081/swagger`

## API Endpoints

### User Management

- `POST /api/users` - Create a new user (ADMIN only)
- `GET /api/users` - Get all users (ADMIN, MANAGER)
- `GET /api/users/{id}` - Get user by ID (ADMIN, MANAGER)
- `GET /api/users/username/{username}` - Get user by username (ADMIN, MANAGER)
- `PUT /api/users/{id}` - Update a user (ADMIN only)
- `DELETE /api/users/{id}` - Delete a user (ADMIN only)
- `PUT /api/users/{id}/password` - Change password (Any authenticated user)

## Default Users

The application creates two default users:

1. **Admin User**
   - Username: `admin`
   - Email: `admin@fipe.com`
   - Password: `admin123`
   - Role: ADMIN

2. **Regular User**
   - Username: `user`
   - Email: `user@fipe.com`
   - Password: `user123`
   - Role: USER

## Packaging and running the application

The application can be packaged using:

```shell
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it's not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/user-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Docker

### Building Docker Images

#### JVM Mode
```shell
./mvnw package
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/user-jvm .
docker run -i --rm -p 8081:8081 quarkus/user-jvm
```

#### Native Mode
```shell
./mvnw package -Dnative
docker build -f src/main/docker/Dockerfile.native -t quarkus/user .
docker run -i --rm -p 8081:8081 quarkus/user
```

## Database Migrations

Database migrations are managed by Liquibase and run automatically on startup.

Migration files are located in: `src/main/resources/db/changelog/changes/`

## Testing

Run tests with:

```shell
./mvnw test
```

## Technology Stack

- **Quarkus 3.30.1**: Application framework
- **Hibernate ORM with Panache**: JPA implementation
- **PostgreSQL**: Primary database
- **Liquibase**: Database migrations
- **SmallRye JWT**: JWT authentication
- **SmallRye OpenAPI**: API documentation
- **REST**: RESTful web services
- **Lombok**: Boilerplate code reduction

## Related Guides

- Hibernate ORM ([guide](https://quarkus.io/guides/hibernate-orm)): Define your persistent model with Hibernate ORM and Jakarta Persistence
- SmallRye JWT ([guide](https://quarkus.io/guides/security-jwt)): Secure your applications with JSON Web Token
- SmallRye OpenAPI ([guide](https://quarkus.io/guides/openapi-swaggerui)): Document your REST APIs with OpenAPI - comes with Swagger UI
- REST ([guide](https://quarkus.io/guides/rest)): A Jakarta REST implementation utilizing build time processing and Vert.x
- JDBC Driver - PostgreSQL ([guide](https://quarkus.io/guides/datasource)): Connect to the PostgreSQL database via JDBC

## Contributing

This is part of the FIPE microservices system. Follow the established architectural patterns when adding new features.
