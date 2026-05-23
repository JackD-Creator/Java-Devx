<template>
  <div class="e-input-wrapper" :class="{ 'e-input-wrapper--error': !!error }">
    <label v-if="label" :for="inputId" class="e-input__label">{{ label }}</label>
    <input
      :id="inputId"
      v-bind="$attrs"
      :value="modelValue"
      :type="type"
      :placeholder="placeholder"
      :disabled="disabled"
      :class="['e-input', `e-input--${size}`]"
      @input="$emit('update:modelValue', ($event.target as HTMLInputElement).value)"
    />
    <span v-if="error" class="e-input__error">{{ error }}</span>
    <span v-else-if="hint" class="e-input__hint">{{ hint }}</span>
  </div>
</template>

<script setup lang="ts">
import type { Size } from '../../types/ui'

defineOptions({ inheritAttrs: false })

const props = withDefaults(defineProps<{
  modelValue?: string | number
  label?:      string
  type?:       string
  placeholder?: string
  size?:       Size
  disabled?:   boolean
  error?:      string
  hint?:       string
}>(), {
  type:     'text',
  size:     'md',
  disabled: false,
})

defineEmits<{ 'update:modelValue': [value: string] }>()

const inputId = useId()
</script>

<style scoped>
.e-input-wrapper { display: flex; flex-direction: column; gap: 0.25rem; }
.e-input__label  { font-size: var(--font-size-sm, 0.875rem); font-weight: var(--font-weight-medium, 500); color: var(--color-text, #111827); }
.e-input {
  width: 100%;
  border: 1px solid var(--color-border, #E5E7EB);
  border-radius: var(--radius-md, 0.375rem);
  font-family: var(--font-family, inherit);
  color: var(--color-text, #111827);
  background: var(--color-background, #fff);
  transition: border-color 0.15s;
  outline: none;
}
.e-input:focus  { border-color: var(--color-primary, #3B82F6); }
.e-input:disabled { opacity: 0.5; cursor: not-allowed; }
.e-input--sm { padding: 0.375rem 0.625rem; font-size: var(--font-size-sm, 0.875rem); }
.e-input--md { padding: 0.5rem 0.75rem;    font-size: var(--font-size-base, 1rem); }
.e-input--lg { padding: 0.625rem 0.875rem; font-size: var(--font-size-lg, 1.125rem); }
.e-input-wrapper--error .e-input { border-color: var(--color-error, #EF4444); }
.e-input__error { font-size: var(--font-size-xs, 0.75rem); color: var(--color-error, #EF4444); }
.e-input__hint  { font-size: var(--font-size-xs, 0.75rem); color: var(--color-text-muted, #6B7280); }
</style>
