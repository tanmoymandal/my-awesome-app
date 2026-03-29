# My Awesome App

[![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=openjdk)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.0-brightgreen?style=flat-square&logo=springboot)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.6%2B-C71A36?style=flat-square&logo=apachemaven)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-blue?style=flat-square)](LICENSE)
[![H2 Database](https://img.shields.io/badge/Database-H2-blue?style=flat-square)](https://www.h2database.com/)
[![Swagger](https://img.shields.io/badge/API%20Docs-Swagger-85EA2D?style=flat-square&logo=swagger)](http://localhost:8080/swagger-ui.html)

A multi-module Spring Boot application demonstrating REST API and batch processing with shared JPA data access layer.

## 📋 Overview

This project consists of three Maven modules:

- **dataaccess** - Shared JPA entities and repositories (H2 in-memory database)
- **api** - REST API service with Swagger documentation
- **batch** - Spring Batch jobs for data processing

## 🏗️ Architecture

```
my-awesome-app/
├── dataaccess/          # Shared library module
│   ├── entities/        # JPA entities (User, Product)
│   └── repositories/    # Spring Data JPA repositories
├── api/                 # REST API module
│   └── controllers/     # REST controllers (User, Product)
└── batch/               # Batch processing module
    └── jobs/            # Batch job configurations
```

## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot 3.5.0**
- **Spring Data JPA**
- **Spring Batch 5**
- **H2 Database** (in-memory)
- **Maven** (with wrapper)
- **SpringDoc OpenAPI 3** (Swagger UI)

## ✅ Prerequisites

- Java 17 or higher
- Maven 3.6+ (or use included Maven wrapper `./mvnw`)

## 🚀 Getting Started

### Build the Project

```bash
# Using Maven wrapper (recommended)
./mvnw clean install

# Or using local Maven
mvn clean install
```

### Run the API Module

```bash
# Using Maven wrapper
./mvnw -pl api spring-boot:run

# Or run the JAR
java -jar api/target/api-1.0.0-SNAPSHOT.jar
```

**API Server:** `http://localhost:8080`

### Run the Batch Module

```bash
# Run all batch jobs
./mvnw -pl batch spring-boot:run

# Run specific job
./mvnw -pl batch spring-boot:run -Dspring-boot.run.arguments="--spring.batch.job.name=userSyncJob"
```

**Batch Jobs:**
- `userSyncJob` - Normalizes user names to uppercase
- `productReportJob` - Generates product inventory report

## 📚 API Documentation

Once the API module is running, access Swagger UI at:

**Swagger UI:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

**OpenAPI JSON:** [http://localhost:8080/api-docs](http://localhost:8080/api-docs)

### Available Endpoints

#### User Controller (`/api/users`)
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/active` - Get active users
- `POST /api/users` - Create new user
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

#### Product Controller (`/api/products`)
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `GET /api/products/search?name={name}` - Search products by name
- `GET /api/products/under-price?price={price}` - Get products under price
- `POST /api/products` - Create new product
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product

## 🗄️ Database Access

Both modules use H2 in-memory databases with web console enabled.

### API Database
- **URL:** [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- **JDBC URL:** `jdbc:h2:mem:apidb`
- **Username:** `sa`
- **Password:** _(empty)_

### Batch Database
- **URL:** [http://localhost:8081/h2-console](http://localhost:8081/h2-console)
- **JDBC URL:** `jdbc:h2:mem:batchdb`
- **Username:** `sa`
- **Password:** _(empty)_

## 🧪 Sample Data

Both modules are pre-populated with test data on startup:

**Users:**
- Alice Johnson (alice@example.com)
- Bob Smith (bob@example.com)
- Carol White (carol@example.com)
- David Brown (david@example.com)
- Eve Davis (eve@example.com)

**Products:**
- Laptop Pro ($1,299.99)
- Wireless Mouse ($29.99)
- USB-C Hub ($49.99)
- Mechanical Keyboard ($89.99)
- Monitor 27" ($599.99)

## 🎯 Quick Test

### Create a User
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com","active":true}'
```

### Get All Products
```bash
curl http://localhost:8080/api/products
```

### Search Products
```bash
curl "http://localhost:8080/api/products/search?name=laptop"
```

## 🖥️ VS Code Launch Configurations

This project includes pre-configured launch settings for VS Code:

- **Launch API** - Starts the REST API on port 8080
- **Launch Batch - User Sync Job** - Runs user sync batch job only
- **Launch Batch - Product Report Job** - Runs product report batch job only
- **Launch Batch - All Jobs** - Runs all batch jobs sequentially
- **Launch API + All Batch Jobs** - Compound configuration to run both modules

Access them via **Run and Debug (⇧⌘D)** panel in VS Code.

## 📁 Project Structure

```
my-awesome-app/
├── .mvn/wrapper/              # Maven wrapper files
├── .vscode/                   # VS Code configurations
│   ├── launch.json           # Debug/run configurations
│   └── settings.json         # Java/Maven settings
├── api/
│   ├── src/main/java/
│   │   └── com/example/myawesomeapp/api/
│   │       ├── ApiApplication.java
│   │       ├── config/
│   │       │   └── OpenApiConfig.java
│   │       └── controller/
│   │           ├── UserController.java
│   │           └── ProductController.java
│   ├── src/main/resources/
│   │   ├── application.yml
│   │   └── data.sql
│   └── pom.xml
├── batch/
│   ├── src/main/java/
│   │   └── com/example/myawesomeapp/batch/
│   │       ├── BatchApplication.java
│   │       └── job/
│   │           ├── UserSyncJobConfig.java
│   │           └── ProductReportJobConfig.java
│   ├── src/main/resources/
│   │   ├── application.yml
│   │   └── data.sql
│   └── pom.xml
├── dataaccess/
│   ├── src/main/java/
│   │   └── com/example/myawesomeapp/dataaccess/
│   │       ├── entity/
│   │       │   ├── User.java
│   │       │   └── Product.java
│   │       └── repository/
│   │           ├── UserRepository.java
│   │           └── ProductRepository.java
│   └── pom.xml
├── mvnw, mvnw.cmd            # Maven wrapper scripts
└── pom.xml                   # Parent POM
```

## 🔧 Configuration

### API Module (application.yml)
- **Server Port:** 8080
- **Database:** H2 in-memory (apidb)
- **Swagger UI:** `/swagger-ui.html`
- **H2 Console:** `/h2-console`

### Batch Module (application.yml)
- **Server Port:** 8081
- **Database:** H2 in-memory (batchdb)
- **Batch Jobs:** Auto-start enabled
- **H2 Console:** `/h2-console`

## 📝 Development Notes

### Adding New Entities
1. Create entity in `dataaccess/src/main/java/.../entity/`
2. Create repository in `dataaccess/src/main/java/.../repository/`
3. Rebuild the dataaccess module: `./mvnw clean install -pl dataaccess`
4. Use in api or batch modules

### Adding New REST Endpoints
1. Create controller in `api/src/main/java/.../controller/`
2. Inject required repositories via constructor
3. Endpoints auto-appear in Swagger UI

### Adding New Batch Jobs
1. Create configuration in `batch/src/main/java/.../job/`
2. Define `@Bean` for Job and Step(s)
3. Launch with `--spring.batch.job.name=yourJobName`

## 🐛 Troubleshooting

**Issue:** Maven not recognized  
**Solution:** Use Maven wrapper: `./mvnw` instead of `mvn`

**Issue:** Repository beans not found  
**Solution:** Ensure `@EntityScan` and `@EnableJpaRepositories` point to `dataaccess` packages

**Issue:** Port 8080 already in use  
**Solution:** Stop other services or change `server.port` in `application.yml`

## 📄 License

This project is a sample application for demonstration purposes.