# enterprise-libs

Monorepo framework untuk enterprise application — backend berbasis Quarkus/Java dan frontend berbasis Nuxt/TypeScript.

## Struktur

```
enterprise-libs/
├── backend-libs/
│   ├── quarkus-db-adapter/       # Base repository, pagination, health check
│   ├── quarkus-encryption/       # AES-256-GCM, RSA-2048, BCrypt, HMAC
│   └── quarkus-theme-api/        # REST API untuk manajemen tema
├── frontend-libs/
│   ├── nuxt-composables/         # useForm, usePermission, useDebounce, dll
│   ├── nuxt-theme-lib/           # Nuxt module tema dinamis dari API
│   └── nuxt-ui-lib/              # Komponen UI: Button, Input, Table, Modal, Badge
├── Makefile
├── package.json
└── SETUP.md
```

## Prasyarat

| Tool        | Versi minimal |
|-------------|---------------|
| Java        | 21            |
| Maven       | 3.9+          |
| Node.js     | 18+           |
| Docker      | 24+ / Rancher Desktop |

Lihat [SETUP.md](SETUP.md) untuk panduan instalasi lengkap.

## Quick Start

```bash
# Install semua dependensi frontend
npm install

# Build semua backend module
mvn clean install -DskipTests

# Build semua frontend module
npm run build --workspaces

# Jalankan semua via Docker Compose
make docker-build-all
```

## Makefile targets

| Target             | Deskripsi                          |
|--------------------|------------------------------------|
| `make build-all`   | Build backend (Maven) + frontend   |
| `make test-all`    | Jalankan semua unit test           |
| `make docker-build-all` | Build semua Docker image      |
| `make clean`       | Hapus semua artefak build          |
| `make release`     | Tag + push release ke registry     |

## Modul Backend

- [quarkus-db-adapter](backend-libs/quarkus-db-adapter/README.md)
- [quarkus-encryption](backend-libs/quarkus-encryption/README.md)
- [quarkus-theme-api](backend-libs/quarkus-theme-api/README.md)

## Modul Frontend

- [nuxt-composables](frontend-libs/nuxt-composables/README.md)
- [nuxt-theme-lib](frontend-libs/nuxt-theme-lib/README.md)
- [nuxt-ui-lib](frontend-libs/nuxt-ui-lib/README.md)

## Variabel Environment

Salin `.env.root` ke `.env` di root:

```env
APP_ENV=development
REGISTRY=ghcr.io/enterprise
VERSION=1.0.0-SNAPSHOT
```

## Lisensi

MIT
