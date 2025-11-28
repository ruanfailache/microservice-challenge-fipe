# FIPE Vehicle Data Management System

A microservices-based system for managing FIPE vehicle data, implementing **Hexagonal Architecture** (Ports and Adapters), following **Clean Architecture**, **DDD**, **SOLID principles**, and **TDD** practices.

## 🏗️ Architecture

This project follows **Hexagonal Architecture** with two microservices:

### Gateway Service (Port 8080)
- **REST API**: Exposes endpoints for triggering initial vehicle data load
- **External Client**: Fetches vehicle brands (marcas) from FIPE API
- **Kafka Producer**: Publishes marca messages for processing
- **Redis**: Caching layer for performance optimization

### Processor Service (Port 8081)
- **Kafka Consumer**: Consumes marca messages from gateway
- **External Client**: Fetches vehicle models (modelos) from FIPE API
- **PostgreSQL**: Persists vehicle data (marca, codigo, modelo)
- **Background Processing**: Asynchronous data processing

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

### Running Locally (Development)

1. **Start infrastructure** (PostgreSQL, Kafka, Redis):
   ```powershell
   docker-compose up -d postgres kafka redis
   ```

2. **Run Gateway** (in terminal 1):
   ```powershell
   cd gateway
   ./mvnw quarkus:dev
   ```

3. **Run Processor** (in terminal 2):
   ```powershell
   cd processor
   ./mvnw quarkus:dev
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
  "role": "ADMIN",
  "expiresIn": 86400
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

## 🧪 Testing

### Run Unit Tests

**Gateway**:
```powershell
cd gateway
./mvnw test
```

**Processor**:
```powershell
cd processor
./mvnw test
```

### Test Coverage

Tests include:
- ✅ Domain model validation tests
- ✅ Use case service tests with mocks
- ✅ Edge cases and error scenarios
- ✅ Repository adapter tests
- ✅ Exception handling tests

### Manual Testing

1. **Start services**:
   ```powershell
   docker-compose up -d
   ```

2. **Wait for services to be ready** (30-60 seconds)

3. **Login and get token**:
   ```powershell
   $response = Invoke-RestMethod -Uri "http://localhost:8080/api/v1/auth/login" -Method POST -ContentType "application/json" -Body '{"username":"admin","password":"admin"}'
   $token = $response.token
   ```

4. **Trigger initial load** (with authentication):
   ```powershell
   Invoke-RestMethod -Uri "http://localhost:8080/api/v1/initial-load" -Method POST -Headers @{Authorization="Bearer $token"}
   ```

5. **Get all brands** (public endpoint):
   ```powershell
   Invoke-RestMethod -Uri "http://localhost:8080/api/v1/brands"
   ```

6. **Get vehicles by brand** (public endpoint, cached):
   ```powershell
   Invoke-RestMethod -Uri "http://localhost:8080/api/v1/vehicles/brand/1"
   ```

7. **Update a vehicle** (with authentication):
   ```powershell
   $updateBody = @{
       model = "Palio 1.0 Fire"
       observations = "Carro com ar-condicionado"
   } | ConvertTo-Json
   
   Invoke-RestMethod -Uri "http://localhost:8080/api/v1/vehicles/1" -Method PUT -Headers @{Authorization="Bearer $token"} -ContentType "application/json" -Body $updateBody
   ```

8. **Check processor logs**:
   ```powershell
   docker-compose logs -f processor
   ```

9. **Verify data in database**:
   ```powershell
   # Gateway database
   docker exec -it postgres psql -U fipe -d fipe_gateway
   SELECT COUNT(*) FROM vehicle;
   SELECT * FROM vehicle LIMIT 10;
   
   # Processor database
   docker exec -it postgres psql -U fipe -d fipe_processor
   SELECT COUNT(*) FROM vehicle_data;
   SELECT * FROM vehicle_data LIMIT 10;
   ```

10. **Check Redis cache**:
    ```powershell
    docker exec -it redis redis-cli
    KEYS *
    GET brands:all
    GET vehicles:brand:1
    ```

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

### Gateway Database (fipe_gateway)

**brand Table:**
```sql
CREATE TABLE brand (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL
);
```

**vehicle Table:**
```sql
CREATE TABLE vehicle (
    id BIGSERIAL PRIMARY KEY,
    brand_code VARCHAR(50) NOT NULL,
    brand_name VARCHAR(255) NOT NULL,
    code VARCHAR(50) NOT NULL,
    model VARCHAR(500) NOT NULL,
    observations TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT uk_vehicle_brand_code UNIQUE (brand_code, code)
);
```

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

### Indexes
- Primary keys on `id` columns
- Unique constraints on (`brand_code`, `code`)
- Indexes on `brand_code` for faster brand queries
- Indexes on `created_at` for time-based queries
- Index on `brand.code` for faster brand lookups

## 🔄 Message Flow

```
┌─────────────┐      ┌─────────────┐      ┌──────────┐      ┌───────────────┐
│   Client    │─────▶│   Gateway   │─────▶│  Kafka   │─────▶│   Processor   │
└─────────────┘      └─────────────┘      └──────────┘      └───────────────┘
                            │                                         │
                            │                                         │
                            ▼                                         ▼
                     ┌─────────────┐                          ┌──────────────┐
                     │  FIPE API   │                          │  PostgreSQL  │
                     │  (Marcas)   │                          │ (VehicleData)│
                     └─────────────┘                          └──────────────┘
                                                                      ▲
                                                                      │
                                                               ┌──────────────┐
                                                               │  FIPE API    │
                                                               │  (Modelos)   │
                                                               └──────────────┘
```

## 📝 Configuration

### Environment Variables

**Gateway**:
- `QUARKUS_HTTP_PORT`: HTTP port (default: 8080)
- `QUARKUS_DATASOURCE_JDBC_URL`: Database URL
- `KAFKA_BOOTSTRAP_SERVERS`: Kafka brokers
- `QUARKUS_REDIS_HOSTS`: Redis connection
- `QUARKUS_REST_CLIENT_FIPE_API_URL`: FIPE API URL

**Processor**:
- `QUARKUS_HTTP_PORT`: HTTP port (default: 8081)
- `QUARKUS_DATASOURCE_JDBC_URL`: Database URL
- `KAFKA_BOOTSTRAP_SERVERS`: Kafka brokers
- `QUARKUS_REST_CLIENT_FIPE_API_URL`: FIPE API URL

## 🐛 Troubleshooting

### Kafka Connection Issues
```powershell
# Check Kafka status
docker-compose ps kafka

# View Kafka logs
docker-compose logs kafka

# Restart Kafka
docker-compose restart kafka
```

### Database Connection Issues
```powershell
# Check PostgreSQL status
docker-compose ps postgres

# Connect to database
docker exec -it postgres psql -U fipe -d fipe_processor

# Check if databases exist
docker exec -it postgres psql -U fipe -c "\l"
```

### Service Not Starting
```powershell
# Check service logs
docker-compose logs gateway
docker-compose logs processor

# Rebuild services
docker-compose build --no-cache
docker-compose up -d
```

## ⚙️ Configuration

Both services use YAML configuration format (`application.yml`) instead of properties files for better readability and structure.

Database schema changes are managed through Liquibase migrations located in `src/main/resources/db/changelog/`. This ensures version-controlled, trackable database evolution across environments.

## 📚 Additional Resources

- [Quarkus Documentation](https://quarkus.io/guides/)
- [FIPE API Documentation](https://deividfortuna.github.io/fipe/)
- [Liquibase Documentation](https://docs.liquibase.com/)
- [Hexagonal Architecture](https://alistair.cockburn.us/hexagonal-architecture/)
- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

## 👥 Contributing

1. Follow the existing architecture and patterns
2. Write tests for new features
3. Update documentation
4. Follow clean code principles
5. Keep commits atomic and descriptive

## 📄 License

This project is for educational purposes.
