<template>
  <span :class="badgeClasses">
    <slot />
  </span>
</template>

<script lang="ts">
import { defineComponent, PropType } from 'vue'

type Color = 'primary' | 'secondary' | 'success' | 'warning' | 'error' | 'info'
type Size  = 'xs' | 'sm' | 'md'

const SIZE_CLASSES: Record<Size, string> = {
  xs: 'px-1.5 py-0.5 text-[0.65rem]',
  sm: 'px-2 py-0.5 text-xs',
  md: 'px-2.5 py-1 text-sm',
}

// Tailwind static classes — must be complete strings for JIT scanner
const COLOR_CLASSES: Record<Color, string> = {
  primary:   'bg-blue-100 text-blue-700',
  secondary: 'bg-gray-100 text-gray-600',
  success:   'bg-green-100 text-green-700',
  warning:   'bg-amber-100 text-amber-700',
  error:     'bg-red-100 text-red-600',
  info:      'bg-sky-100 text-sky-600',
}

export default defineComponent({
  name: 'EBadge',

  props: {
    color: { type: String as PropType<Color>, default: 'primary' },
    size:  { type: String as PropType<Size>,  default: 'sm' },
  },

  computed: {
    badgeClasses(): string {
      return [
        'inline-flex items-center rounded-full font-medium',
        SIZE_CLASSES[this.size as Size]   ?? SIZE_CLASSES.sm,
        COLOR_CLASSES[this.color as Color] ?? COLOR_CLASSES.primary,
      ].join(' ')
    },
  },
})
</script>
