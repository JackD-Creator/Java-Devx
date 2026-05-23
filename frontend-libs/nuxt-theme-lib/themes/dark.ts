import type { ThemeTokens } from '../src/runtime/types/theme'

export const darkTokens: ThemeTokens = {
  colors: {
    primary:    '#60A5FA',
    secondary:  '#9CA3AF',
    accent:     '#A78BFA',
    background: '#111827',
    surface:    '#1F2937',
    text:       '#F9FAFB',
    textMuted:  '#9CA3AF',
    border:     '#374151',
    error:      '#F87171',
    success:    '#34D399',
    warning:    '#FBBF24',
    info:       '#60A5FA',
  },
  typography: {
    fontFamily: "'Inter', sans-serif",
    fontSize: {
      xs:    '0.75rem',
      sm:    '0.875rem',
      base:  '1rem',
      lg:    '1.125rem',
      xl:    '1.25rem',
      '2xl': '1.5rem',
    },
    fontWeight: {
      normal:   '400',
      medium:   '500',
      semibold: '600',
      bold:     '700',
    },
  },
  spacing: {
    xs: '0.25rem',
    sm: '0.5rem',
    md: '1rem',
    lg: '1.5rem',
    xl: '2rem',
  },
  borderRadius: {
    sm:   '0.25rem',
    md:   '0.375rem',
    lg:   '0.5rem',
    full: '9999px',
  },
}
