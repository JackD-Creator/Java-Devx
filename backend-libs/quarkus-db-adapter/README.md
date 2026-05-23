# quarkus-db-adapter

Library Quarkus untuk abstraksi database — menyediakan base entity, generic repository dengan pagination, dan health check endpoint.

## Fitur

- `BaseEntity` — superclass dengan field `id`, `createdAt`, `updatedAt`, `createdBy`, `updatedBy`
- `PageResult<T>` — wrapper response paginasi generik
- `BaseRepository<E>` — abstract repository dengan method paginasi, saveOrUpdate, deleteById
- `DatabaseHealthCheck` — liveness + readiness probe via JDBC

## Instalasi

Tambahkan dependency ke `pom.xml` project Quarkus Anda:

```xml
<dependency>
  <groupId>com.enterprise</groupId>
  <artifactId>quarkus-db-adapter</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</dependency>
```

## Penggunaan

### BaseEntity

```java
@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    public String name;
    public BigDecimal price;
}
```

Field yang sudah tersedia secara otomatis:
| Field       | Tipe              | Keterangan                      |
|-------------|-------------------|---------------------------------|
| id          | Long              | SEQUENCE, allocationSize=50     |
| createdAt   | LocalDateTime     | Di-set otomatis saat persist    |
| updatedAt   | LocalDateTime     | Di-set otomatis saat update     |
| createdBy   | String            | Dari CDI UserContext (opsional) |
| updatedBy   | String            | Dari CDI UserContext (opsional) |

### BaseRepository

```java
@ApplicationScoped
public class ProductRepository extends BaseRepository<Product> {
    // Semua method sudah tersedia, tambahkan query custom di sini
    public List<Product> findByCategory(String category) {
        return list("category", category);
    }
}
```

Method yang tersedia:
```java
PageResult<E> findAllPaginated(int page, int size)
PageResult<E> findByQueryPaginated(String query, int page, int size, Object... params)
E saveOrUpdate(E entity)
boolean deleteById(Long id)
boolean existsById(Long id)
```

### PageResult

```java
// Contoh response JSON
{
  "data": [...],
  "total": 100,
  "page": 0,
  "size": 20,
  "totalPages": 5
}
```

### Health Check

Endpoint otomatis tersedia di:
- `GET /q/health/live` — liveness probe
- `GET /q/health/ready` — readiness probe

## Konfigurasi

```properties
# application.properties
quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=postgres
quarkus.datasource.password=secret
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/mydb

quarkus.hibernate-orm.database.generation=validate
quarkus.flyway.migrate-at-start=true
```

## Menjalankan dengan Docker

```bash
docker-compose up -d
```

Lihat [docker-compose.yml](docker-compose.yml) untuk konfigurasi lengkap.

## Build

```bash
mvn clean package
# Native build (perlu GraalVM)
mvn clean package -Pnative
```
