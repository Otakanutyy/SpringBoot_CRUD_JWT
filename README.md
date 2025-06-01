# CRUD_back_springBoot

A simple CRUD backend built with Spring Boot, PostgreSQL, JWT authentication with access/refresh flow, and Flyway migrations.  
Docker Compose is used for easy local development and deployment.
OpenApi for documentation
---

## 🚀 Quick Start

### 1. **Clone or Download This Repository**

```
git clone https://github.com/Otakanutyy/SpringBoot_React_CRUD_JWT
```
```
cd CRUD_back_springBoot
```
### 2. Build the Project
   You must build the JAR before running Docker:
   ```
  mvn clean package 
   ```
### 3. Start the Application with Docker Compose
   This runs both the backend and PostgreSQL as containers:
   ```
   docker-compose up --build
   ```
#### The backend will be available at: http://localhost:8080
#### The Swagger UI will be available at: http://localhost:8080/swagger-ui.html

### 4. Stopping the Application
 ####  To stop all containers:
```
docker-compose down
```
#### To remove all containers and database data:
```
docker-compose down -v
```

## ⚡ Useful Commands
### Enter Postgres container:
```
docker-compose exec db psql -U postgres -d todo
```
### Query user table:
#### In psql, run:
```
SELECT * FROM "user";
```

### 📝 Configuration
#### The default Postgres database is todo, user is postgres, password is 509465.
#### Database and app credentials can be adjusted in docker-compose.yml.
#### environment variables (including DB connection) are injected from Docker Compose.