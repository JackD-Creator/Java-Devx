# nuxt-theme-lib

Nuxt module untuk tema dinamis — mengambil design tokens dari `quarkus-theme-api` dan mengaplikasikannya sebagai CSS custom properties ke seluruh aplikasi.

## Instalasi

```bash
npm install nuxt-theme-lib
```

Tambahkan ke `nuxt.config.ts`:

```ts
export default defineNuxtConfig({
  modules: ['nuxt-theme-lib'],

  themeLib: {
    apiBaseUrl: 'http://localhost:8080',   // URL quarkus-theme-api
    cacheTtl: 300,                          // Cache TTL dalam detik (default: 300)
    defaultTheme: 'default',               // Slug tema fallback
  },
})
```

## Penggunaan

### Composable `useTheme`

```vue
<script setup lang="ts">
const { activeTheme, allThemes, fetchActiveTheme, setThemeLocally } = useTheme()
</script>

<template>
  <div>
    <p>Tema aktif: {{ activeTheme?.name }}</p>

    <!-- Ganti tema -->
    <button
      v-for="theme in allThemes"
      :key="theme.id"
      @click="setThemeLocally(theme)"
    >
      {{ theme.name }}
    </button>
  </div>
</template>
```

### API Composable

| Fungsi / State      | Tipe                    | Deskripsi                              |
|---------------------|-------------------------|----------------------------------------|
| `activeTheme`       | `Ref<Theme \| null>`    | Tema yang sedang aktif                 |
| `allThemes`         | `Ref<Theme[]>`          | Semua tema dari API                    |
| `fetchActiveTheme`  | `() => Promise<void>`   | Ambil tema aktif dari API              |
| `fetchAllThemes`    | `() => Promise<void>`   | Ambil semua tema dari API              |
| `setThemeLocally`   | `(t: Theme) => void`    | Terapkan tema tanpa API call           |
| `applyToDocument`   | `(t: Theme) => void`    | Tulis CSS variables ke `<html>`        |

### CSS Custom Properties

Setelah tema diterapkan, variabel CSS tersedia di seluruh aplikasi:

```css
/* Warna */
--color-primary: #3B82F6;
--color-secondary: #6366F1;
--color-background: #FFFFFF;
--color-text: #111827;

/* Tipografi */
--font-family: Inter, sans-serif;
--font-size-base: 16px;

/* Spacing */
--spacing-sm: 8px;
--spacing-md: 16px;
--spacing-lg: 24px;

/* Border radius */
--radius-sm: 4px;
--radius-md: 8px;
```

Gunakan dalam CSS/SCSS:

```css
.my-button {
  background-color: var(--color-primary);
  font-family: var(--font-family);
  border-radius: var(--radius-md);
}
```

## Tema Bawaan

Module menyertakan dua tema bawaan sebagai preset:

```ts
import defaultTheme from 'nuxt-theme-lib/themes/default'
import darkTheme from 'nuxt-theme-lib/themes/dark'
```

## Tipe Data

```ts
interface Theme {
  id: number
  name: string
  slug: string
  active: boolean
  mode: 'light' | 'dark'
  tokensJson: ThemeTokens
  tenantId?: string
}

interface ThemeTokens {
  colors: ThemeColors
  typography: ThemeTypography
  spacing: ThemeSpacing
  borderRadius: ThemeBorderRadius
}
```

## SSR Support

Module ini sepenuhnya SSR-compatible. Tema di-fetch saat server boot via plugin Nuxt dan di-hydrate ke client tanpa flash.

## Build

```bash
npm run build
npm run dev   # mode development dengan hot reload
```
