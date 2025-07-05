# Digital Banking Application

An end-to-end microservice-based digital banking platform, with:

- **Frontend**: React SPA  
- **API Gateway & Auth**: Django REST (JWT)  
- **Core Services**: Spring Boot microservices for Accounts, Transactions, Notifications  
- **Messaging**: Kafka (async events) & gRPC (sync calls)  
- **Persistence**: PostgreSQL per service, Redis cache  
- **Containerization**: Docker & Docker Compose

---

## Table of Contents

1. [Architecture](#architecture)  
2. [Tech Stack](#tech-stack)  
3. [Prerequisites](#prerequisites)  
4. [Getting Started](#getting-started)  
5. [Environment Variables](#environment-variables)  
6. [Docker Compose](#docker-compose)  
7. [Service Details](#service-details)  
8. [API Endpoints](#api-endpoints)  
9. [Running Migrations & Superuser](#running-migrations--superuser)  
10. [Accessing the App](#accessing-the-app)  
11. [Project Structure](#project-structure)  
12. [Troubleshooting](#troubleshooting)  
13. [Contributing](#contributing)  
14. [License](#license)  

---

## Architecture

```
┌─────────┐      ┌─────────────┐
│ React   │◀────▶│ Django REST │  Authentication & API Gateway
└─────────┘      └─────────────┘
     │                │
     ▼                ▼
┌───────────────┐   ┌───────────────┐
│ Transaction   │─▶ │ Account       │   (gRPC sync calls)
│ Service       │   │ Service       │
└───────────────┘   └───────────────┘
     │   ▲                │
     │   │                ▼
     │   │           ┌───────────────┐
     │   └─────────▶ │ Notification  │   (Kafka consumers)
     │               │ Service       │
     ▼               └───────────────┘
┌───────────────┐
│ PostgreSQL    │  (per service)
│ & Redis       │
└───────────────┘
      │
      ▼
   Kafka
```

---

## Tech Stack

- **Frontend**: React, Axios/gRPC-Web, React Router, React Context  
- **Auth/API Gateway**: Django, Django REST Framework, SimpleJWT, django-redis  
- **Services**: Spring Boot, Spring Data JPA, gRPC (yidongnan starter), Spring Kafka  
- **Database**: PostgreSQL  
- **Cache**: Redis  
- **Messaging**: Kafka & Zookeeper  
- **Containerization**: Docker, Docker Compose  

---

## Prerequisites

- Docker & Docker Compose (v1.27+)  
- (Optional) Java 17 SDK & Maven (for local builds)  
- Node.js 18+ & npm/yarn (for local frontend dev)  

---

## Getting Started

```bash
# 1. Clone the repo
git clone https://github.com/your-org/digital-banking-app.git
cd digital-banking-app

# 2. Copy & edit .env files if needed
cp auth_service/.env.example auth_service/.env
cp frontend/.env.example frontend/.env

# 3. Start databases & message broker
docker-compose up -d postgres-account postgres-transaction postgres-auth redis zookeeper kafka

# 4. Run migrations & create superuser
./migrations.sh       # applies Django & other migrations
./superuser.sh        # bootstraps Django superuser

# 5. Launch all services
docker-compose up -d
```

---

## Environment Variables

Each service reads its own `.env` (or compose `environment:`). Key vars include:

| Service             | Variable                         | Description                     |
|---------------------|----------------------------------|---------------------------------|
| **auth (Django)**   | `POSTGRES_HOST`, `POSTGRES_DB`   | Auth DB connection              |
|                     | `REDIS_URL`                      | Redis cache/session store       |
|                     | `DJANGO_SUPERUSER_*`             | Auto-create admin user          |
| **account-service** | `DB_HOST`, `DB_NAME`, `DB_USER`  | Accounts DB                     |
|                     | `KAFKA_BOOTSTRAP_SERVERS`        | Kafka broker address            |
| **transaction-service** | `DB_HOST`, `DB_NAME`, `DB_USER`| Transactions DB                 |
|                     | `ACCOUNT_SERVICE_HOST`, `ACCOUNT_SERVICE_PORT` | gRPC lookup   |
|                     | `KAFKA_BOOTSTRAP_SERVERS`        | Kafka                           |
| **notification-service** | `KAFKA_BOOTSTRAP_SERVERS`    | Kafka                           |
|                     | `MAIL_HOST`, `MAIL_PORT`, `MAIL_USER`, `MAIL_PASS` | SMTP config |

Refer to each service’s `application.yml` or `settings.py` for full details.

---

## Docker Compose

The top-level `docker-compose.yml` defines:

- **zookeeper & kafka**  
- **postgres-account**, **postgres-transaction**, **postgres-auth**  
- **redis**  
- **auth**, **account-service**, **transaction-service**, **notification-service**  
- **frontend**

Bring up everything with:

```bash
docker-compose up -d
```

Tear down with:

```bash
docker-compose down
```

---

## Service Details

- **auth** (Django REST)  
  - Port **8000**  
  - Endpoints: `/api/auth/login/`, `/api/auth/me/`, `/api/auth/logout/`

- **account-service** (gRPC + Kafka)  
  - gRPC port **9090**  
  - Exposes `GetBalance`, `UpdateBalance`, `ListStatements`

- **transaction-service** (REST + gRPC + Kafka)  
  - HTTP port **9092**: `POST /api/transactions`

- **notification-service** (Kafka consumer)  
  - gRPC port **9093** (unused)  
  - Listens on topics `transactions` & `account-updates`

- **frontend** (React)  
  - HTTP port **3000**

---

## API Endpoints

### Authentication (Django)

```http
POST /api/auth/login/
Content-Type: application/json

{
  "username": "admin",
  "password": "YourSecureP@ssw0rd"
}
```

Returns:
```json
{ "access": "...", "refresh": "..." }
```

### Transactions (Spring Boot)

```http
POST http://localhost:9092/api/transactions
Content-Type: application/json
Authorization: Bearer <access_token>

{
  "accountId": "1234",
  "amount": 500.0,
  "type": "DEBIT"
}
```

Response:
```json
{ "transactionId": "...", "newBalance": 1500.0 }
```

---

## Running Migrations & Superuser

- **Django** migrations /	superuser:
```
docker-compose run auth_service python manage.py makemigrations users
docker-compose run auth_service python manage.py migrate
docker-compose run auth_service python manage.py createsuperuser
```

- **Spring Boot** services auto-apply schema updates (`spring.jpa.hibernate.ddl-auto=update`).

---

## Accessing the App

- **React UI**:  `http://localhost:3000`  
- **Django Admin**: `http://localhost:8000/admin`  
- **Jaeger UI** (if enabled): `http://localhost:16686`  
- **Prometheus** (if enabled): `http://localhost:9090`  

---

## Project Structure

```
├── auth_service/
├── account-service/
├── transaction-service/
├── notification-service/
├── frontend/
└── docker-compose.yml
```

Each directory contains its own Dockerfile, source code, and config.

---

## Troubleshooting

- **Ports in use**: ensure no other processes on 3000, 8000, 9090–9093.  
- **DB startup**: if Django fails to connect, increase wait in `migrations.sh`.  
- **Kafka errors**: verify Zookeeper & Kafka health via logs.  

---

## Contributing

1. Fork ▶️ Clone ▶️ Create feature branch  
2. Build & test locally with Docker Compose  
3. Open a Pull Request  

Please follow existing code style & write tests for new features.

---

## License

This project is MIT-licensed. See [LICENSE](LICENSE) for details.
