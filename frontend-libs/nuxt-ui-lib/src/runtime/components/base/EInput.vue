<template>
  <div :class="wrapperClasses">
    <label
      v-if="label"
      :for="uid"
      class="text-sm font-medium text-e-text"
    >
      {{ label }}
    </label>
    <input
      :id="uid"
      v-bind="$attrs"
      :value="modelValue"
      :type="type"
      :placeholder="placeholder"
      :disabled="disabled"
      :class="inputClasses"
      @input="$emit('update:modelValue', ($event.target as HTMLInputElement).value)"
    />
    <span v-if="hasError" class="text-xs text-danger">{{ error }}</span>
    <span v-else-if="hint" class="text-xs text-e-muted">{{ hint }}</span>
  </div>
</template>

<script lang="ts">
import { defineComponent, PropType } from 'vue'

type Size = 'sm' | 'md' | 'lg'

const SIZE_CLASSES: Record<Size, string> = {
  sm: 'px-2.5 py-1.5 text-sm',
  md: 'px-3 py-2 text-base',
  lg: 'px-3.5 py-2.5 text-lg',
}

let uidCounter = 0

export default defineComponent({
  name: 'EInput',

  inheritAttrs: false,

  props: {
    modelValue:  { type: [String, Number], default: '' },
    label:       { type: String, default: '' },
    type:        { type: String, default: 'text' },
    placeholder: { type: String, default: '' },
    size:        { type: String as PropType<Size>, default: 'md' },
    disabled:    { type: Boolean, default: false },
    error:       { type: String, default: '' },
    hint:        { type: String, default: '' },
  },

  emits: ['update:modelValue'],

  data() {
    return { uid: `e-input-${++uidCounter}` }
  },

  computed: {
    hasError(): boolean {
      return !!this.error
    },

    wrapperClasses(): string {
      return 'flex flex-col gap-1'
    },

    inputClasses(): string {
      return [
        'w-full rounded-md font-sans transition-colors outline-none',
        'text-e-text bg-e-bg',
        'focus:ring-1 focus:ring-primary focus:border-primary',
        'disabled:opacity-50 disabled:cursor-not-allowed',
        SIZE_CLASSES[this.size as Size] ?? SIZE_CLASSES.md,
        this.hasError
          ? 'border border-danger'
          : 'border border-e-border',
      ].join(' ')
    },
  },
})
</script>
