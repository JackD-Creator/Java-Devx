import type { Config } from 'tailwindcss'

export default {
  content: [
    './src/runtime/components/**/*.{vue,ts}',
  ],
  theme: {
    extend: {
      colors: {
        primary:   'var(--color-primary)',
        secondary: 'var(--color-secondary)',
        success:   'var(--color-success)',
        warning:   'var(--color-warning)',
        danger:    'var(--color-danger)',
        info:      'var(--color-info)',
      },
      fontFamily: {
        sans: ['var(--font-family)', 'sans-serif'],
      },
      borderRadius: {
        DEFAULT: 'var(--radius-md)',
        sm:      'var(--radius-sm)',
        md:      'var(--radius-md)',
        lg:      'var(--radius-lg)',
      },
    },
  },
  plugins: [],
} satisfies Config
