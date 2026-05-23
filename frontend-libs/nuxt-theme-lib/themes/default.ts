import type { ThemeTokens } from '../src/runtime/types/theme'

export const defaultTokens: ThemeTokens = {
  colors: {
    primary:    '#3B82F6',
    secondary:  '#6B7280',
    accent:     '#8B5CF6',
    background: '#FFFFFF',
    surface:    '#F9FAFB',
    text:       '#111827',
    textMuted:  '#6B7280',
    border:     '#E5E7EB',
    error:      '#EF4444',
    success:    '#10B981',
    warning:    '#F59E0B',
    info:       '#3B82F6',
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
