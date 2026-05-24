# Contributing to enterprise-libs

> Developed by **PT. Temp Solusi Kreasi** — © 2026. All Rights Reserved.

Terima kasih telah berkontribusi! Panduan ini membantu tim berkolaborasi dengan konsisten.

---

## Daftar Isi

- [Setup Development](#setup-development)
- [Struktur Branch](#struktur-branch)
- [Standar Commit](#standar-commit)
- [Backend (Java/Quarkus)](#backend-javaquarkus)
- [Frontend (Nuxt/TypeScript)](#frontend-nuxttypescript)
- [Pull Request](#pull-request)
- [Code Review Checklist](#code-review-checklist)

---

## Setup Development

### Prasyarat

| Tool | Versi Minimum |
|------|--------------|
| Java (Eclipse Temurin) | 21 |
| Maven | 3.9+ |
| Node.js | 18+ |
| Docker (Rancher Desktop / Docker Desktop) | 24+ |
| Git | 2.40+ |

### Clone & Install

```bash
git clone https://github.com/JackD-Creator/Java-Devx.git
cd Java-Devx/enterprise-libs

# Frontend dependencies
npm install

# Backend build (skip tests untuk setup awal)
mvn clean install -DskipTests
```

### Jalankan Lokal

```bash
# Theme API dengan PostgreSQL
cd backend-libs/quarkus-theme-api
docker compose up -d

# API tersedia di http://localhost:8080
# Swagger UI di http://localhost:8080/q/swagger-ui
```

---

## Struktur Branch

```
main          ← production-ready, hanya via PR
develop       ← integrasi semua fitur
feature/*     ← fitur baru: feature/add-theme-export
fix/*         ← bug fix: fix/docker-db-url
hotfix/*      ← critical fix langsung ke main
release/*     ← persiapan release: release/1.1.0
```

**Aturan:**
- Branch dari `develop`, merge ke `develop`
- Hotfix: branch dari `main`, merge ke `main` + `develop`
- Hapus branch setelah merge
- Nama branch lowercase, gunakan `-` bukan `_` atau spasi

---

## Standar Commit

Format: **Conventional Commits** (`https://www.conventionalcommits.org`)

```
<type>(<scope>): <deskripsi singkat>

[body opsional]

[footer opsional]
```

### Tipe Commit

| Tipe | Kapan dipakai |
|------|--------------|
| `feat` | Fitur baru |
| `fix` | Bug fix |
| `docs` | Perubahan dokumentasi |
| `style` | Format/style (tidak mengubah logika) |
| `refactor` | Refactor tanpa tambah fitur/fix |
| `test` | Tambah/update test |
| `chore` | Build, CI, dependency update |
| `perf` | Peningkatan performa |

### Scope

- Backend: `db-adapter`, `encryption`, `theme-api`
- Frontend: `ui-lib`, `composables`, `theme-lib`
- Infra: `docker`, `ci`, `deps`

### Contoh

```
feat(theme-api): tambah endpoint export tema ke JSON
fix(db-adapter): perbaiki koneksi pool timeout di MariaDB
docs(ui-lib): update contoh penggunaan ETable dengan slot
chore(deps): update Quarkus ke 3.16.0
```

---

## Backend (Java/Quarkus)

### Konvensi Kode

- **Options/Style:** Google Java Style Guide
- **Package:** `com.enterprise.<module>.<layer>` (misal: `com.enterprise.theme.model`)
- **Naming:** PascalCase untuk class, camelCase untuk method/field, UPPER_SNAKE untuk konstanta
- **Javadoc:** wajib untuk semua `public` class dan method yang non-trivial

### Struktur Modul

```
src/main/java/com/enterprise/<module>/
  ├── model/          # Entity & DTO
  ├── repository/     # Data access layer
  ├── service/        # Business logic
  ├── resource/       # REST endpoint
  └── config/         # Konfigurasi Quarkus
src/main/resources/
  ├── application.properties
  └── db/migration/<vendor>/
src/test/
```

### Test

```bash
# Unit test semua modul
mvn test

# Test spesifik modul
mvn test -pl backend-libs/quarkus-theme-api

# Test dengan coverage
mvn verify
```

---

## Frontend (Nuxt/TypeScript)

### Konvensi Kode

- **Style:** Options API (bukan Composition API) untuk semua komponen `.vue`
- **Styling:** Tailwind CSS utility classes — **tidak ada** custom CSS baru
- **Naming:** PascalCase untuk komponen (`EButton.vue`), camelCase untuk method/data
- **TypeScript:** strict mode, tidak ada `any` kecuali terpaksa

### Pola Options API

```vue
<script lang="ts">
import { defineComponent, PropType } from 'vue'

export default defineComponent({
  name: 'NamaKomponen',

  props: {
    value: { type: String as PropType<'a' | 'b'>, default: 'a' },
  },

  emits: ['update:value'],

  data() {
    return { localState: '' }
  },

  computed: {
    derivedValue(): string { return this.value.toUpperCase() },
  },

  methods: {
    handleAction() { this.$emit('update:value', this.localState) },
  },
})
</script>
```

### Pola Mixin (untuk state yang di-share)

```ts
import { PaginationMixin, FormMixin } from 'nuxt-composables/mixins'

export default defineComponent({
  mixins: [PaginationMixin, FormMixin],
  // ...
})
```

### Build & Lint

```bash
# Build semua frontend module
npm run build --workspaces

# Lint
npm run lint --workspaces

# Type check
npm run type-check --workspaces
```

---

## Pull Request

1. **Buat branch** dari `develop`
2. **Commit** dengan Conventional Commits
3. **Push** ke remote
4. **Buka PR** ke `develop` (bukan `main`)
5. **Isi PR template** — pastikan semua checklist tercentang
6. **Request review** minimal 1 reviewer
7. **Squash & Merge** setelah approved

### PR Title Format

```
feat(theme-api): tambah endpoint export tema ke JSON
```

---

## Code Review Checklist

Reviewer wajib memverifikasi:

### Backend
- [ ] Tidak ada `System.out.println` (gunakan `@Inject Logger`)
- [ ] Semua endpoint punya `@Operation` Swagger
- [ ] Tidak ada N+1 query (gunakan JOIN atau batch load)
- [ ] Input validation di boundary (Resource layer)
- [ ] Migration SQL tersedia untuk semua 3 vendor (postgresql/mariadb/mssql)

### Frontend
- [ ] Komponen menggunakan Options API (bukan `<script setup>`)
- [ ] Hanya Tailwind classes — tidak ada `<style scoped>` baru
- [ ] Props terdefinisi dengan tipe yang jelas
- [ ] Emits terdaftar di `emits: [...]`
- [ ] Tidak ada `any` tanpa komentar penjelasan

### Umum
- [ ] Tidak ada secrets/credentials di kode
- [ ] `.env.example` diupdate jika ada env var baru
- [ ] README diupdate jika ada perubahan API publik
- [ ] Semua test lulus

---

*© 2026 PT. Temp Solusi Kreasi — All Rights Reserved*
