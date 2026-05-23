<template>
  <span :class="['e-badge', `e-badge--${color}`, `e-badge--${size}`]">
    <slot />
  </span>
</template>

<script setup lang="ts">
import type { Color, Size } from '../../types/ui'
withDefaults(defineProps<{ color?: Color; size?: Size }>(), { color: 'primary', size: 'sm' })
</script>

<style scoped>
.e-badge { display: inline-flex; align-items: center; border-radius: var(--radius-full, 9999px); font-weight: var(--font-weight-medium, 500); }
.e-badge--xs { padding: 0.1rem 0.4rem; font-size: 0.65rem; }
.e-badge--sm { padding: 0.15rem 0.5rem; font-size: var(--font-size-xs, 0.75rem); }
.e-badge--md { padding: 0.25rem 0.625rem; font-size: var(--font-size-sm, 0.875rem); }
.e-badge--primary   { background: color-mix(in srgb, var(--color-primary, #3B82F6) 15%, transparent); color: var(--color-primary, #3B82F6); }
.e-badge--secondary { background: color-mix(in srgb, var(--color-secondary, #6B7280) 15%, transparent); color: var(--color-secondary, #6B7280); }
.e-badge--success   { background: color-mix(in srgb, var(--color-success, #10B981) 15%, transparent); color: var(--color-success, #10B981); }
.e-badge--warning   { background: color-mix(in srgb, var(--color-warning, #F59E0B) 15%, transparent); color: var(--color-warning, #F59E0B); }
.e-badge--error     { background: color-mix(in srgb, var(--color-error, #EF4444) 15%, transparent); color: var(--color-error, #EF4444); }
.e-badge--info      { background: color-mix(in srgb, var(--color-info, #3B82F6) 15%, transparent); color: var(--color-info, #3B82F6); }
</style>
