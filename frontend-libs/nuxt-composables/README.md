# nuxt-composables

Kumpulan composable Vue 3 / Nuxt 3 siap pakai untuk kebutuhan enterprise — form validation, paginasi, permission, localStorage, debounce, dan throttle.

## Instalasi

```bash
npm install nuxt-composables
```

Tambahkan ke `nuxt.config.ts`:

```ts
export default defineNuxtConfig({
  modules: ['nuxt-composables'],
})
```

Semua composable langsung tersedia via auto-import.

## Composables

### `useForm`

Form dengan validasi, error tracking, dan submit handler.

```vue
<script setup lang="ts">
const { values, errors, touched, loading, isValid, validate, touch, reset, handleSubmit } =
  useForm(
    { email: '', password: '' },
    {
      email: [
        v => !!v || 'Email wajib diisi',
        v => String(v).includes('@') || 'Format email tidak valid',
      ],
      password: [
        v => !!v || 'Password wajib diisi',
        v => String(v).length >= 8 || 'Minimal 8 karakter',
      ],
    }
  )

async function onSubmit() {
  await handleSubmit(async (data) => {
    await $fetch('/api/login', { method: 'POST', body: data })
  })
}
</script>

<template>
  <EInput v-model="values.email" :error="errors.email" @blur="touch('email')" />
  <EInput v-model="values.password" :error="errors.password" type="password" />
  <EButton :loading="loading" :disabled="!isValid" @click="onSubmit">Login</EButton>
</template>
```

### `usePermission`

Manajemen role dan permission berbasis Nuxt `useState` (SSR-safe).

```vue
<script setup lang="ts">
const { hasRole, hasPermission, hasAll, setPermissions, isAdmin, auth } = usePermission()

// Set permissions (biasanya dari response login)
setPermissions({
  roles: ['editor', 'viewer'],
  permissions: ['post:read', 'post:write', 'comment:read'],
})
</script>

<template>
  <div v-if="isAdmin">Panel Admin</div>
  <div v-if="hasPermission('post:write')">Tombol Edit</div>
  <div v-if="hasAll('post:read', 'comment:read')">Semua izin tersedia</div>
</template>
```

| Fungsi            | Deskripsi                                      |
|-------------------|------------------------------------------------|
| `hasRole(...)`    | True jika user memiliki salah satu role        |
| `hasPermission(…)`| True jika user memiliki salah satu permission  |
| `hasAll(...)`     | True jika user memiliki SEMUA permission       |
| `isAdmin`         | Computed: true jika role `admin` atau `superadmin` |
| `setPermissions`  | Set roles + permissions (biasanya post-login)  |

### `usePagination`

```vue
<script setup lang="ts">
const { page, size, total, totalPages, hasNext, hasPrevious, next, previous, goTo, setTotal, reset } =
  usePagination({ page: 0, size: 20 })

const { data } = await useFetch('/api/users', {
  query: { page: page.value, size: size.value },
  watch: [page, size],
})

// Saat data berhasil diambil
setTotal(data.value?.total ?? 0)
</script>

<template>
  <ETable :rows="data?.items" />

  <div class="pagination">
    <EButton :disabled="!hasPrevious" @click="previous">Prev</EButton>
    <span>{{ page + 1 }} / {{ totalPages }}</span>
    <EButton :disabled="!hasNext" @click="next">Next</EButton>
  </div>
</template>
```

### `useLocalStorage`

```ts
// Nilai otomatis disimpan ke localStorage saat berubah
const theme = useLocalStorage('user-theme', 'default')
const sidebar = useLocalStorage('sidebar-open', true)

theme.value = 'dark'   // langsung tersimpan
```

SSR-safe: tidak mengakses `window` di server.

### `useDebounce` & `useThrottle`

```ts
const search = ref('')
const debouncedSearch = useDebounce(search, 400)   // tunggu 400ms setelah user berhenti mengetik

watch(debouncedSearch, (val) => {
  fetchResults(val)
})
```

```ts
const scrollY = ref(0)
const throttledScroll = useThrottle(scrollY, 100)  // update max 1x per 100ms

window.addEventListener('scroll', () => {
  scrollY.value = window.scrollY
})
```

## API Lengkap

### `useForm`

| Return Value   | Tipe                   | Deskripsi                              |
|----------------|------------------------|----------------------------------------|
| `values`       | `reactive<T>`          | Nilai form                             |
| `errors`       | `reactive<Errors<T>>`  | Error per field                        |
| `touched`      | `reactive<...>`        | Field yang sudah disentuh user         |
| `loading`      | `Ref<boolean>`         | True saat `handleSubmit` berjalan      |
| `isValid`      | `ComputedRef<boolean>` | True jika tidak ada error              |
| `validate()`   | `() => boolean`        | Jalankan validasi semua field          |
| `touch(field)` | `(keyof T) => void`    | Tandai field sebagai touched           |
| `reset()`      | `() => void`           | Reset ke initialValues                 |
| `handleSubmit` | `(fn) => Promise<void>`| Validasi lalu jalankan fn              |

### `useDebounce<T>(source, delay?)`

| Parameter | Default | Deskripsi              |
|-----------|---------|------------------------|
| `source`  | —       | `Ref<T>` sumber        |
| `delay`   | `300`   | Delay dalam ms         |

### `useThrottle<T>(source, interval?)`

| Parameter  | Default | Deskripsi              |
|------------|---------|------------------------|
| `source`   | —       | `Ref<T>` sumber        |
| `interval` | `300`   | Interval minimum ms    |

## Build

```bash
npm run build
npm run dev
```
