<template>
  <button
    v-bind="$attrs"
    :class="buttonClasses"
    :disabled="disabled || loading"
    :type="type"
    @click="$emit('click', $event)"
  >
    <span
      v-if="loading"
      class="animate-spin inline-block w-4 h-4 border-2 border-current border-t-transparent rounded-full"
      aria-hidden="true"
    />
    <slot />
  </button>
</template>

<script lang="ts">
import { defineComponent, PropType } from 'vue'

type Variant = 'primary' | 'secondary' | 'accent' | 'ghost' | 'danger'
type Size    = 'xs' | 'sm' | 'md' | 'lg' | 'xl'

const SIZE_CLASSES: Record<Size, string> = {
  xs: 'px-2 py-1 text-xs',
  sm: 'px-3 py-1.5 text-sm',
  md: 'px-4 py-2 text-base',
  lg: 'px-5 py-2.5 text-lg',
  xl: 'px-6 py-3 text-xl',
}

const VARIANT_CLASSES: Record<Variant, string> = {
  primary:   'bg-primary text-white hover:opacity-90',
  secondary: 'bg-secondary text-white hover:opacity-90',
  accent:    'bg-accent text-white hover:opacity-90',
  ghost:     'bg-transparent text-primary border border-primary hover:bg-primary hover:text-white',
  danger:    'bg-danger text-white hover:opacity-90',
}

export default defineComponent({
  name: 'EButton',

  inheritAttrs: false,

  props: {
    variant:  { type: String as PropType<Variant>, default: 'primary' },
    size:     { type: String as PropType<Size>,    default: 'md' },
    disabled: { type: Boolean, default: false },
    loading:  { type: Boolean, default: false },
    type:     { type: String as PropType<'button' | 'submit' | 'reset'>, default: 'button' },
    block:    { type: Boolean, default: false },
  },

  emits: ['click'],

  computed: {
    buttonClasses(): string {
      return [
        'inline-flex items-center justify-center gap-2',
        'font-medium rounded-md transition-colors cursor-pointer',
        'disabled:opacity-50 disabled:cursor-not-allowed',
        SIZE_CLASSES[this.size as Size]          ?? SIZE_CLASSES.md,
        VARIANT_CLASSES[this.variant as Variant] ?? VARIANT_CLASSES.primary,
        this.block ? 'w-full' : '',
      ].filter(Boolean).join(' ')
    },
  },
})
</script>
