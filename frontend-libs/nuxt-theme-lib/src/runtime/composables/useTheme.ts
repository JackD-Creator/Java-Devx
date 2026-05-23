import { ref, computed, readonly } from 'vue'
import { useRuntimeConfig, useFetch, useState } from '#app'
import type { Theme, ThemeResolved, ThemeTokens } from '../types/theme'

function parseTheme(raw: Theme): ThemeResolved {
  return {
    ...raw,
    tokens: JSON.parse(raw.tokensJson || '{}') as ThemeTokens,
  }
}

function toCssVariables(tokens: ThemeTokens): Record<string, string> {
  return {
    '--color-primary':     tokens.colors.primary,
    '--color-secondary':   tokens.colors.secondary,
    '--color-accent':      tokens.colors.accent,
    '--color-background':  tokens.colors.background,
    '--color-surface':     tokens.colors.surface,
    '--color-text':        tokens.colors.text,
    '--color-text-muted':  tokens.colors.textMuted,
    '--color-border':      tokens.colors.border,
    '--color-error':       tokens.colors.error,
    '--color-success':     tokens.colors.success,
    '--color-warning':     tokens.colors.warning,
    '--color-info':        tokens.colors.info,
    '--font-family':       tokens.typography.fontFamily,
    '--font-size-xs':      tokens.typography.fontSize.xs,
    '--font-size-sm':      tokens.typography.fontSize.sm,
    '--font-size-base':    tokens.typography.fontSize.base,
    '--font-size-lg':      tokens.typography.fontSize.lg,
    '--font-size-xl':      tokens.typography.fontSize.xl,
    '--font-size-2xl':     tokens.typography.fontSize['2xl'],
    '--font-weight-normal':    tokens.typography.fontWeight.normal,
    '--font-weight-medium':    tokens.typography.fontWeight.medium,
    '--font-weight-semibold':  tokens.typography.fontWeight.semibold,
    '--font-weight-bold':      tokens.typography.fontWeight.bold,
    '--spacing-xs':  tokens.spacing.xs,
    '--spacing-sm':  tokens.spacing.sm,
    '--spacing-md':  tokens.spacing.md,
    '--spacing-lg':  tokens.spacing.lg,
    '--spacing-xl':  tokens.spacing.xl,
    '--radius-sm':   tokens.borderRadius.sm,
    '--radius-md':   tokens.borderRadius.md,
    '--radius-lg':   tokens.borderRadius.lg,
    '--radius-full': tokens.borderRadius.full,
  }
}

function applyToDocument(tokens: ThemeTokens) {
  if (typeof document === 'undefined') return
  const vars = toCssVariables(tokens)
  const root = document.documentElement
  for (const [key, value] of Object.entries(vars)) {
    root.style.setProperty(key, value)
  }
}

export function useTheme() {
  const config = useRuntimeConfig()
  const baseUrl = config.public.themeLib?.apiBaseUrl ?? ''

  const activeTheme = useState<ThemeResolved | null>('theme:active', () => null)
  const allThemes   = useState<ThemeResolved[]>('theme:all', () => [])
  const isLoading   = ref(false)
  const error       = ref<Error | null>(null)

  const cssVariables = computed(() =>
    activeTheme.value ? toCssVariables(activeTheme.value.tokens) : {}
  )

  async function fetchActiveTheme() {
    isLoading.value = true
    error.value = null
    try {
      const { data, error: fetchError } = await useFetch<Theme>(`${baseUrl}/api/themes/active`)
      if (fetchError.value) throw new Error(fetchError.value.message)
      if (data.value) {
        activeTheme.value = parseTheme(data.value)
        applyToDocument(activeTheme.value.tokens)
      }
    } catch (e) {
      error.value = e as Error
    } finally {
      isLoading.value = false
    }
  }

  async function fetchAllThemes() {
    const { data } = await useFetch<Theme[]>(`${baseUrl}/api/themes`)
    if (data.value) {
      allThemes.value = data.value.map(parseTheme)
    }
  }

  function setThemeLocally(themeId: number | string) {
    const found = allThemes.value.find(t => t.id === themeId)
    if (!found) return
    activeTheme.value = found
    applyToDocument(found.tokens)
  }

  return {
    activeTheme: readonly(activeTheme),
    allThemes:   readonly(allThemes),
    cssVariables,
    isLoading:   readonly(isLoading),
    error:       readonly(error),
    fetchActiveTheme,
    fetchAllThemes,
    setThemeLocally,
  }
}
