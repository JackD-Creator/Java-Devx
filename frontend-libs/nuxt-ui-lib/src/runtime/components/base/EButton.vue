<template>
  <button
    :class="classes"
    :disabled="disabled || loading"
    :type="type"
    v-bind="$attrs"
    @click="$emit('click', $event)"
  >
    <span v-if="loading" class="e-btn__spinner" aria-hidden="true" />
    <slot />
  </button>
</template>

<script setup lang="ts">
import type { Size, Variant } from '../../types/ui'

defineOptions({ inheritAttrs: false })

const props = withDefaults(defineProps<{
  variant?:  Variant
  size?:     Size
  disabled?: boolean
  loading?:  boolean
  type?:     'button' | 'submit' | 'reset'
  block?:    boolean
}>(), {
  variant:  'primary',
  size:     'md',
  disabled: false,
  loading:  false,
  type:     'button',
  block:    false,
})

defineEmits<{ click: [e: MouseEvent] }>()

const classes = computed(() => [
  'e-btn',
  `e-btn--${props.variant}`,
  `e-btn--${props.size}`,
  { 'e-btn--block': props.block, 'e-btn--loading': props.loading },
])
</script>

<style scoped>
.e-btn {
  display:        inline-flex;
  align-items:    center;
  justify-content:center;
  gap:            0.5rem;
  border:         none;
  border-radius:  var(--radius-md, 0.375rem);
  font-family:    var(--font-family, inherit);
  font-weight:    var(--font-weight-medium, 500);
  cursor:         pointer;
  transition:     background 0.15s, opacity 0.15s;
}
.e-btn:disabled { opacity: 0.5; cursor: not-allowed; }
.e-btn--block   { width: 100%; }

/* Sizes */
.e-btn--xs { padding: 0.25rem 0.5rem;  font-size: var(--font-size-xs, 0.75rem); }
.e-btn--sm { padding: 0.375rem 0.75rem; font-size: var(--font-size-sm, 0.875rem); }
.e-btn--md { padding: 0.5rem 1rem;      font-size: var(--font-size-base, 1rem); }
.e-btn--lg { padding: 0.625rem 1.25rem; font-size: var(--font-size-lg, 1.125rem); }
.e-btn--xl { padding: 0.75rem 1.5rem;   font-size: var(--font-size-xl, 1.25rem); }

/* Variants */
.e-btn--primary   { background: var(--color-primary, #3B82F6);   color: #fff; }
.e-btn--secondary { background: var(--color-secondary, #6B7280); color: #fff; }
.e-btn--accent    { background: var(--color-accent, #8B5CF6);    color: #fff; }
.e-btn--ghost     { background: transparent; color: var(--color-primary, #3B82F6); border: 1px solid currentColor; }
.e-btn--danger    { background: var(--color-error, #EF4444);     color: #fff; }

.e-btn__spinner {
  width: 1em; height: 1em;
  border: 2px solid transparent;
  border-top-color: currentColor;
  border-radius: 50%;
  animation: spin 0.6s linear infinite;
}
@keyframes spin { to { transform: rotate(360deg); } }
</style>
