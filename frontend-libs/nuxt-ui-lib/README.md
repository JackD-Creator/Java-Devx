# nuxt-ui-lib

Library komponen UI enterprise untuk Nuxt 3 — terintegrasi dengan `nuxt-theme-lib` via CSS custom properties.

## Instalasi

```bash
npm install nuxt-ui-lib
```

Tambahkan ke `nuxt.config.ts`:

```ts
export default defineNuxtConfig({
  modules: ['nuxt-ui-lib'],
})
```

Semua komponen langsung tersedia tanpa import manual.

## Komponen

### EButton

```vue
<EButton variant="primary" size="md" @click="doSomething">
  Simpan
</EButton>

<EButton variant="outline" :loading="isLoading" block>
  Loading...
</EButton>
```

| Prop       | Tipe                                              | Default     |
|------------|---------------------------------------------------|-------------|
| `variant`  | `primary \| secondary \| outline \| ghost \| danger` | `primary`   |
| `size`     | `xs \| sm \| md \| lg \| xl`                     | `md`        |
| `loading`  | `boolean`                                         | `false`     |
| `disabled` | `boolean`                                         | `false`     |
| `block`    | `boolean`                                         | `false`     |

### EInput

```vue
<EInput
  v-model="email"
  label="Email"
  type="email"
  placeholder="user@example.com"
  :error="errors.email"
  hint="Gunakan email aktif"
/>
```

| Prop          | Tipe      | Keterangan                 |
|---------------|-----------|----------------------------|
| `modelValue`  | `string`  | v-model binding            |
| `label`       | `string`  | Label field                |
| `error`       | `string`  | Pesan error (merah)        |
| `hint`        | `string`  | Teks bantuan (abu-abu)     |
| `disabled`    | `boolean` | Nonaktifkan input          |

### ETable

```vue
<ETable
  :columns="[
    { key: 'name', label: 'Nama', sortable: true },
    { key: 'email', label: 'Email' },
    { key: 'status', label: 'Status' },
  ]"
  :rows="users"
  :loading="isLoading"
>
  <template #cell-status="{ value }">
    <EBadge :color="value === 'active' ? 'green' : 'red'">
      {{ value }}
    </EBadge>
  </template>
</ETable>
```

| Prop       | Tipe              | Keterangan                   |
|------------|-------------------|------------------------------|
| `columns`  | `TableColumn[]`   | Definisi kolom               |
| `rows`     | `T[]`             | Data baris (generic)         |
| `loading`  | `boolean`         | Tampilkan skeleton loader    |

Slot per kolom: `#cell-{key}` menerima `{ row, value }`.

### EModal

```vue
<EModal v-model="isOpen" title="Konfirmasi Hapus">
  <template #body>
    <p>Yakin ingin menghapus item ini?</p>
  </template>
  <template #footer>
    <EButton variant="ghost" @click="isOpen = false">Batal</EButton>
    <EButton variant="danger" @click="confirmDelete">Hapus</EButton>
  </template>
</EModal>
```

| Prop             | Tipe      | Default | Keterangan                     |
|------------------|-----------|---------|--------------------------------|
| `modelValue`     | `boolean` | —       | v-model untuk buka/tutup       |
| `title`          | `string`  | —       | Judul modal                    |
| `closeOnOverlay` | `boolean` | `true`  | Tutup saat klik latar belakang |

Slot: `#header`, `#body`, `#footer`

### EBadge

```vue
<EBadge color="green">Aktif</EBadge>
<EBadge color="red">Nonaktif</EBadge>
<EBadge color="blue">Baru</EBadge>
```

Warna tersedia: `gray`, `red`, `yellow`, `green`, `blue`, `purple`

### EPageContainer

```vue
<EPageContainer title="Manajemen Pengguna" subtitle="Kelola semua pengguna">
  <template #header>
    <EButton @click="openCreateModal">+ Tambah</EButton>
  </template>

  <!-- konten halaman -->
  <ETable ... />
</EPageContainer>
```

| Prop       | Tipe     | Keterangan          |
|------------|----------|---------------------|
| `title`    | `string` | Judul halaman       |
| `subtitle` | `string` | Subjudul (opsional) |

Slot: `#header` (tombol aksi kanan atas), slot default (konten).

## Kustomisasi Tema

Semua komponen menggunakan CSS custom properties dari `nuxt-theme-lib`. Untuk override:

```css
/* assets/overrides.css */
:root {
  --color-primary: #FF6B35;    /* Ganti warna primary */
  --radius-md: 12px;           /* Ganti border radius */
}
```

## Build

```bash
npm run build
npm run dev
```
