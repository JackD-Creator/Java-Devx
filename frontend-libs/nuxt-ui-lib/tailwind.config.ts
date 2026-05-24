import type { Config } from 'tailwindcss'

export default {
  content: [
    './src/runtime/components/**/*.{vue,ts}',
  ],
  theme: {
    extend: {
      colors: {
        primary:    'var(--color-primary)',
        secondary:  'var(--color-secondary)',
        accent:     'var(--color-accent)',
        success:    'var(--color-success)',
        warning:    'var(--color-warning)',
        danger:     'var(--color-error)',
        info:       'var(--color-info)',
        surface:    'var(--color-surface)',
        // e-* prefix avoids collision with Tailwind built-ins
        'e-border': 'var(--color-border)',
        'e-text':   'var(--color-text)',
        'e-muted':  'var(--color-textMuted)',
        'e-bg':     'var(--color-background)',
      },
      fontFamily: {
        sans: ['var(--font-family)', 'sans-serif'],
      },
      borderRadius: {
        sm:      'var(--radius-sm)',
        DEFAULT: 'var(--radius-md)',
        md:      'var(--radius-md)',
        lg:      'var(--radius-lg)',
        full:    'var(--radius-full)',
      },
      spacing: {
        'e-xs': 'var(--spacing-xs)',
        'e-sm': 'var(--spacing-sm)',
        'e-md': 'var(--spacing-md)',
        'e-lg': 'var(--spacing-lg)',
        'e-xl': 'var(--spacing-xl)',
      },
    },
  },
  plugins: [],
} satisfies Config
