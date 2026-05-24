## Deskripsi

<!-- Jelaskan perubahan yang dilakukan dan alasannya -->

## Tipe Perubahan

- [ ] `feat` — Fitur baru
- [ ] `fix` — Bug fix
- [ ] `refactor` — Refactor (tidak ada perubahan fungsional)
- [ ] `docs` — Perubahan dokumentasi
- [ ] `chore` — Build, CI, dependency

## Modul yang Terpengaruh

- [ ] `quarkus-db-adapter`
- [ ] `quarkus-encryption`
- [ ] `quarkus-theme-api`
- [ ] `nuxt-ui-lib`
- [ ] `nuxt-composables`
- [ ] `nuxt-theme-lib`
- [ ] Infrastruktur / CI / Docker

## Checklist Backend (jika ada perubahan Java)

- [ ] Komponen menggunakan Options API (bukan `<script setup>`)
- [ ] Tidak ada `System.out.println` — gunakan `@Inject Logger`
- [ ] Semua endpoint REST punya `@Operation` Swagger
- [ ] Migration SQL tersedia untuk postgresql / mariadb / mssql
- [ ] Unit test ditambahkan / diupdate

## Checklist Frontend (jika ada perubahan Vue/TS)

- [ ] Komponen menggunakan Options API (`defineComponent({})`)
- [ ] Styling hanya Tailwind classes — tidak ada `<style scoped>` baru
- [ ] Props terdefinisi dengan tipe TypeScript yang jelas
- [ ] Emits terdaftar di `emits: [...]`

## Checklist Umum

- [ ] Tidak ada secrets/credentials di kode
- [ ] `.env.example` diupdate jika ada env var baru
- [ ] README diupdate jika ada perubahan API publik
- [ ] Semua test lulus (`mvn test` / `npm run test`)
- [ ] Build berhasil (`mvn clean install -DskipTests`)

## Screenshot / Demo (opsional)

<!-- Tambahkan screenshot atau output terminal jika relevan -->

## Issue Terkait

Closes #<!-- nomor issue -->
