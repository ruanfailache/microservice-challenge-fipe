# FIPE Vehicle Data Management System

A microservices-based system for managing FIPE vehicle data, implementing **Hexagonal Architecture** (Ports and Adapters), following **Clean Architecture**, **DDD**, **SOLID principles**, and **TDD** practices.

## 🏗️ Architecture

This project follows **Hexagonal Architecture** with three microservices:

### Gateway Service (Port 8080)
- **REST API**: Exposes endpoints for triggering initial vehicle data load
- **External Client**: Fetches vehicle brands (marcas) from FIPE API
- **Kafka Producer**: Publishes marca messages for processing
- **Redis**: Caching layer for performance optimization
- **User Service Client**: Communicates with User Service for authentication

### Processor Service (Port 8081)
- **Kafka Consumer**: Consumes marca messages from gateway
- **External Client**: Fetches vehicle models (modelos) from FIPE API
- **PostgreSQL**: Persists vehicle data (marca, codigo, modelo)
- **Background Processing**: Asynchronous data processing
- **User Service Client**: Communicates with User Service for authentication

### User Service (Port 8082)
- **REST API**: Provides user management endpoints (CRUD operations)
- **Authentication**: JWT-based authentication with RSA keys
- **Authorization**: Role-based access control (ADMIN, USER, MANAGER, VIEWER)
- **PostgreSQL**: Stores user data
- **Password Management**: Secure password encoding and validation

## 📂 Project Structure

Both services follow the same hexagonal architecture structure:

```
src/main/java/com/fipe/
├── domain/                          # Domain Layer (Core Business Logic)
│   ├── model/                       # Domain entities and value objects
│   ├── port/
│   │   ├── in/                     # Input ports (use cases)
│   │   └── out/                    # Output ports (repositories, clients)
│   └── exception/                   # Domain exceptions
├── application/                     # Application Layer (Use Cases)
│   ├── service/                     # Use case implementations
│   └── usecase/                     # Use case interfaces
└── infrastructure/                  # Infrastructure Layer (Technical Details)
    ├── adapter/
    │   ├── in/
    │   │   ├── rest/               # REST controllers, DTOs, mappers
    │   │   └── messaging/          # Kafka consumers
    │   └── out/
    │       ├── persistence/        # Database adapters (JPA)
    │       ├── messaging/          # Kafka producers
    │       ├── client/             # External API clients
    │       └── cache/              # Redis cache adapters
    ├── config/                      # Configuration classes
    └── security/                    # Security configurations
```

## 🚀 Technologies

- **Java 17**: Programming language
- **Quarkus 3.30.1**: Framework (supersonic subatomic Java)
- **PostgreSQL 16**: Relational database
- **Apache Kafka**: Message broker for async communication
- **Redis 7**: Cache layer
- **Hibernate ORM with Panache**: Database access
- **RESTEasy Reactive**: REST implementation
- **SmallRye Reactive Messaging**: Kafka integration
- **SmallRye OpenAPI**: API documentation
- **JUnit 5 + Mockito**: Testing framework
- **Docker & Docker Compose**: Containerization

## 📋 Features

### Authentication

- **JWT-based authentication** using RSA keys
- **Two user roles**: `ADMIN` and `USER`
- **Protected endpoints** with role-based access control
- **Demo credentials**:
  - Admin: `username: admin`, `password: admin`
  - User: `username: user`, `password: user`

### Initial Load Process

1. **Gateway receives REST request** to trigger initial load (requires ADMIN role)
   ```
   POST /api/v1/initial-load
   Authorization: Bearer <token>
   ```

2. **Gateway fetches all brands** from FIPE API
   ```
   GET https://parallelum.com.br/fipe/api/v1/carros/marcas
   ```

3. **Gateway publishes messages** to Kafka topic `vehicle-data`
   - One message per brand (marca)
   - Message contains: `marcaCodigo`, `marcaNome`

4. **Processor consumes messages** from Kafka

5. **Processor fetches models** for each brand from FIPE API
   ```
   GET https://parallelum.com.br/fipe/api/v1/carros/marcas/{marcaId}/modelos
   ```

6. **Processor saves data** to PostgreSQL
   - Table: `vehicle_data`
   - Columns: `id`, `brand_code`, `brand_name`, `code`, `model`, `observations`, `created_at`, `updated_at`
   - Unique constraint: (`brand_code`, `code`)

### Query Operations

- **Get all brands** from database (cached with Redis)
- **Get vehicles by brand** with code, model, and observations (cached with Redis)
- **Search vehicles** with filtering capabilities

### Update Operations

- **Update vehicle data** including model and observations (requires authentication)
- **Automatic cache invalidation** on updates

## 🛠️ Setup & Running

### Prerequisites
- Docker & Docker Compose
- Java 17 (for local development)
- Maven 3.9+ (for local development)

### Running with Docker Compose

1. **Start all services**:
   ```powershell
   docker-compose up -d
   ```

2. **View logs**:
   ```powershell
   # All services
   docker-compose logs -f
   
   # Specific service
   docker-compose logs -f gateway
   docker-compose logs -f processor
   ```

3. **Stop services**:
   ```powershell
   docker-compose down
   ```

## 📖 API Documentation

### Gateway API

- **Swagger UI**: http://localhost:8080/swagger-ui
- **OpenAPI Spec**: http://localhost:8080/swagger

#### Authentication

**Login:**
```http
POST http://localhost:8080/api/v1/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin"
}

Response 200 OK:
{
  "token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...",
  "username": "admin",
  "role": "ADMIN"
}
```

#### Brand Operations

**Get all brands:**
```http
GET http://localhost:8080/api/v1/brands

Response 200 OK:
[
  {
    "code": "1",
    "name": "Fiat"
  },
  {
    "code": "2",
    "name": "Volkswagen"
  }
]
```

**Get brand by code:**
```http
GET http://localhost:8080/api/v1/brands/{code}

Response 200 OK:
{
  "code": "1",
  "name": "Fiat"
}
```

#### Vehicle Operations

**Get vehicles by brand:**
```http
GET http://localhost:8080/api/v1/vehicles/brand/{brandCode}

Response 200 OK:
[
  {
    "id": 1,
    "brandCode": "1",
    "brandName": "Fiat",
    "code": "001",
    "model": "Palio 1.0",
    "observations": "Carro em bom estado",
    "createdAt": "2025-11-27T10:00:00",
    "updatedAt": "2025-11-27T10:00:00"
  }
]
```

**Get vehicle by ID:**
```http
GET http://localhost:8080/api/v1/vehicles/{id}

Response 200 OK:
{
  "id": 1,
  "brandCode": "1",
  "brandName": "Fiat",
  "code": "001",
  "model": "Palio 1.0",
  "observations": "Carro em bom estado",
  "createdAt": "2025-11-27T10:00:00",
  "updatedAt": "2025-11-27T10:00:00"
}
```

**Update vehicle (requires authentication):**
```http
PUT http://localhost:8080/api/v1/vehicles/{id}
Authorization: Bearer <token>
Content-Type: application/json

{
  "model": "Palio 1.0 Fire",
  "observations": "Carro com ar-condicionado"
}

Response 200 OK:
{
  "id": 1,
  "brandCode": "1",
  "brandName": "Fiat",
  "code": "001",
  "model": "Palio 1.0 Fire",
  "observations": "Carro com ar-condicionado",
  "createdAt": "2025-11-27T10:00:00",
  "updatedAt": "2025-11-27T11:30:00"
}
```

#### Initial Load (requires ADMIN role)

**Trigger initial load:**
```http
POST http://localhost:8080/api/v1/initial-load
Authorization: Bearer <token>
Content-Type: application/json

Response 202 Accepted:
{
  "message": "Initial load triggered successfully",
  "brandsProcessed": 150,
  "status": "PROCESSING"
}
```

### Processor API

- **Swagger UI**: http://localhost:8081/swagger-ui
- **OpenAPI Spec**: http://localhost:8081/swagger

### User Service API

- **Swagger UI**: http://localhost:8082/swagger-ui
- **OpenAPI Spec**: http://localhost:8082/swagger

## 🎯 Design Patterns Used

- **Hexagonal Architecture (Ports & Adapters)**: Separation of concerns
- **Repository Pattern**: Data access abstraction
- **Dependency Injection**: Loose coupling
- **Factory Pattern**: Object creation
- **Strategy Pattern**: Algorithm selection
- **Mapper Pattern**: DTO/Entity conversion
- **Template Method**: Code reuse in base classes

## 🔒 SOLID Principles

- **Single Responsibility**: Each class has one reason to change
- **Open/Closed**: Open for extension, closed for modification
- **Liskov Substitution**: Subtypes must be substitutable
- **Interface Segregation**: Small, focused interfaces
- **Dependency Inversion**: Depend on abstractions, not concretions

## 📊 Database Schema

### Processor Database (fipe_processor)

**vehicle_data Table:**
```sql
CREATE TABLE vehicle_data (
    id BIGSERIAL PRIMARY KEY,
    brand_code VARCHAR(50) NOT NULL,
    brand_name VARCHAR(255) NOT NULL,
    code VARCHAR(50) NOT NULL,
    model VARCHAR(500) NOT NULL,
    observations TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT uk_vehicle_data UNIQUE (brand_code, code)
);
```

### User Service Database (fipe_user)

**user Table:**
```sql
CREATE TABLE "user" (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
```

### Indexes
- Primary keys on `id` columns
- Unique constraints on (`brand_code`, `code`)
- Indexes on `brand_code` for faster brand queries
- Indexes on `created_at` for time-based queries
- Unique index on `user.username` for faster user lookups

## 🔄 Message Flow

Overview
- The `Gateway` is an external service and must NOT access the `Processor` database directly.
- All communication between Gateway and Processor uses Kafka (messaging).
- There are two distinct flows: Command (initial-load) and DQL (query — request/reply).

1) Command Flow — Initial Load (high level steps)
   1. Gateway authenticates the request with the User Service (REST/JWT).
   2. Gateway publishes an initial-load command message to Kafka topic `commands.initial-load` (one message per brand). Message payload should include `brandCode`, `brandName` and `userCtx`.
   3. Processor (Kafka consumer) receives the command message.
   4. Processor fetches models from FIPE API (GET /carros/marcas/{brandId}/modelos).
   5. Processor transforms and persists vehicle records into PostgreSQL (`fipe_processor` database).
   6. Processor may optionally publish domain events (for example `events.vehicle-created`) to Kafka for downstream consumers.

2) DQL Flow — Query (request/reply) (high level steps)
   1. Gateway receives a client query (HTTP) and authenticates it using User Service / JWT.
   2. Gateway publishes a DQL request message to Kafka topic `dql.requests`. The message MUST include `correlationId` and a `replyTopic` (or follow a convention such as `dql.responses`) and can include `filters` and `userCtx`.
   3. Processor (Kafka consumer) receives the DQL request and validates/authorizes using the provided `userCtx`.
   4. Processor queries PostgreSQL (and/or Redis cache if configured) to produce the response payload.
   5. Processor publishes the response message to the `replyTopic` with the same `correlationId` and the response payload.
   6. Gateway consumes the response message from the reply topic and returns the result to the end user.

Topics (recommended)
- `commands.initial-load` — initial-load command messages (one per brand).
- `events.vehicle-created` — optional domain events emitted after persistence.
- `dql.requests` — query/request messages from Gateway.
- `dql.responses` — response messages published by Processor (or per-request replyTopic).

Minimal message examples
- Initial-load command:
  {
    "brandCode": "1",
    "brandName": "Fiat",
    "userCtx": { "userId": "...", "roles": ["ADMIN"] }
  }

- DQL request:
  {
    "brandCode": "1",
    "filters": { "model": "Palio" },
    "correlationId": "uuid-1234",
    "replyTopic": "dql.responses",
    "userCtx": { "userId": "...", "roles": ["USER"] }
  }

- DQL response:
  {
    "correlationId": "uuid-1234",
    "payload": { "vehicles": [ /* ... */ ] }
  }

Notes
- Gateway must never directly access the Processor database or cache; it acts as an external messaging client.
- Processor is responsible for authorization checks when messages include `userCtx`.
