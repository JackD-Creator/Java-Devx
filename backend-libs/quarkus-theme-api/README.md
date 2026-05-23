# quarkus-theme-api

REST API untuk manajemen tema dinamis — menyimpan design tokens (warna, tipografi, spacing) di database dan menyajikannya ke frontend.

## Fitur

- CRUD tema via REST API
- Aktivasi tema (hanya satu aktif dalam satu waktu)
- Design tokens lengkap: warna, tipografi, spacing, border radius
- Multi-tenant support via `tenantId`
- Migrasi database otomatis via Flyway
- Seed data: tema `default` dan `dark`

## Endpoint API

| Method | Path                        | Deskripsi                        |
|--------|-----------------------------|----------------------------------|
| GET    | `/api/themes`               | Daftar semua tema                |
| GET    | `/api/themes/active`        | Tema yang sedang aktif           |
| GET    | `/api/themes/{id}`          | Detail tema by ID                |
| POST   | `/api/themes`               | Buat tema baru                   |
| PUT    | `/api/themes/{id}`          | Update tema                      |
| PUT    | `/api/themes/{id}/activate` | Aktifkan tema ini                |
| DELETE | `/api/themes/{id}`          | Hapus tema                       |

## Contoh Request

### Ambil tema aktif
```bash
curl http://localhost:8080/api/themes/active
```

### Response
```json
{
  "id": 1,
  "name": "Default",
  "slug": "default",
  "active": true,
  "mode": "light",
  "tokensJson": {
    "colors": {
      "primary": "#3B82F6",
      "secondary": "#6366F1"
    },
    "typography": {
      "fontFamily": "Inter, sans-serif",
      "fontSizeBase": "16px"
    }
  },
  "tenantId": null
}
```

### Buat tema baru
```bash
curl -X POST http://localhost:8080/api/themes \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Brand Tema",
    "slug": "brand",
    "mode": "light",
    "tokensJson": {
      "colors": { "primary": "#FF6B35" }
    }
  }'
```

### Aktifkan tema
```bash
curl -X PUT http://localhost:8080/api/themes/2/activate
```

## Skema Database

```sql
CREATE TABLE themes (
  id          BIGSERIAL PRIMARY KEY,
  name        VARCHAR(100) NOT NULL,
  slug        VARCHAR(100) NOT NULL UNIQUE,
  active      BOOLEAN DEFAULT false,
  mode        VARCHAR(20) DEFAULT 'light',
  tokens_json JSONB,
  tenant_id   VARCHAR(100),
  created_at  TIMESTAMP DEFAULT NOW(),
  updated_at  TIMESTAMP DEFAULT NOW()
);
```

## Konfigurasi

```properties
# application.properties
quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=${DB_USER:postgres}
quarkus.datasource.password=${DB_PASS:postgres}
quarkus.datasource.jdbc.url=jdbc:postgresql://${DB_HOST:localhost}:${DB_PORT:5432}/${DB_NAME:theme_db}

quarkus.flyway.migrate-at-start=true
quarkus.hibernate-orm.database.generation=validate
```

## Menjalankan dengan Docker

```bash
# Jalankan database + API
docker-compose up -d

# Cek log
docker-compose logs -f app
```

## Build

```bash
mvn clean package
mvn clean package -Pnative   # GraalVM native binary
```

## Environment Variables

Salin `.env.example` ke `.env`:

```env
DB_HOST=localhost
DB_PORT=5432
DB_NAME=theme_db
DB_USER=postgres
DB_PASS=postgres
```
