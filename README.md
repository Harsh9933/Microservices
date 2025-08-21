# Microservices Stack

Spring Boot microservices with Eureka discovery, API Gateway, and per-service databases. Docker Compose orchestrates the full stack.

## Services and Ports

- Service Registry (Eureka): http://localhost:8761
- API Gateway: http://localhost:8088
- Product Service (HTTP + MongoDB): http://localhost:8080
- Inventory Service (HTTP + PostgreSQL): http://localhost:8082
- Order Service (HTTP + PostgreSQL): http://localhost:8081
- MongoDB: localhost:27017 (container: `mongo`)
- Postgres (Inventory): host localhost:5430 (container: `postgres-inventory`)
- Postgres (Order): host localhost:5431 (container: `postgres-order`)

## Quick Start

```bash
# From repo root Build and start everything
docker compose up --build

# Check status
docker compose ps
```

Stop the stack:
```bash
docker compose down
```

## API Access

Through the gateway (recommended for clients):
- Product routes: GET/POST/DELETE under `http://localhost:8088/api/product`
- Inventory routes: `http://localhost:8088/api/inventory`
- Order routes: `http://localhost:8088/api/order`

Direct-to-service (useful for debugging):
- Product: `http://localhost:8080/api/product`
- Inventory: `http://localhost:8082/api/inventory`
- Order: `http://localhost:8081/api/order`

Example requests:

```bash
# List products via gateway
curl http://localhost:8088/api/product

# Create a product directly on the service
curl -X POST http://localhost:8080/api/product \
  -H 'Content-Type: application/json' \
  -d '{"name":"Demo","description":"Test","price":99.99}'

# Delete a product via gateway (replace {id})
curl -X DELETE http://localhost:8088/api/product/{id}
```

## OpenAPI / Swagger

Per-service Swagger UIs (working):
- Product: `http://localhost:8080/swagger-ui.html` (OpenAPI JSON: `http://localhost:8080/v3/api-docs`)
- Inventory: `http://localhost:8082/swagger-ui.html` (OpenAPI JSON: `http://localhost:8082/v3/api-docs`)
- Order: `http://localhost:8081/swagger-ui.html` (OpenAPI JSON: `http://localhost:8081/v3/api-docs`)

Gateway centralized Swagger UI: currently not working.
- Aggregated OpenAPI JSON is available via the gateway:
  - Product: `http://localhost:8088/openapi/product`
  - Inventory: `http://localhost:8088/openapi/inventory`
  - Order: `http://localhost:8088/openapi/order`
- Intended (when fixed): `http://localhost:8088/swagger-ui.html` (or `/swagger-ui/index.html`)


## Build Notes

- All services target Java 21. Dockerfiles use Temurin JDK 21 for build/runtime.
- Images build with tests skipped to avoid Testcontainers during Docker builds.

Rebuild everything:
```bash
docker compose up -d --build
```

Rebuild a single service:
```bash
docker compose up -d --build product-service
```

## Databases and Data

- Named volumes persist DB data: `pgdata_inventory`, `pgdata_order`, `mongodata`.
- To reset data, remove volumes: `docker compose down -v`.

## Troubleshooting

- Build fails due to tests: confirm Dockerfiles include `mvn -q -DskipTests package`.
- Port conflicts: stop prior containers or apps bound to the same host ports.
- Service discovery: verify Eureka at `http://localhost:8761`.

## Project Structure

- `service-registery/` — Eureka server
- `gateway/` — Spring Cloud Gateway (reactive)
- `product-service/` — REST + MongoDB
- `inventory-service/` — REST + PostgreSQL
- `order-service/` — REST + PostgreSQL
- `compose.yml` — Top-level Docker Compose orchestrator

---
Maintainer notes: centralized Swagger UI at gateway is wired but currently not rendering; use per-service UIs and the gateway-proxied JSON endpoints until resolved.
