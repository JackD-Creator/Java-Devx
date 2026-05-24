<template>
  <Teleport to="body">
    <Transition
      enter-active-class="transition-opacity duration-200"
      enter-from-class="opacity-0"
      leave-active-class="transition-opacity duration-200"
      leave-to-class="opacity-0"
    >
      <div
        v-if="modelValue"
        class="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/50"
        @click.self="onOverlayClick"
      >
        <div
          class="w-full max-h-[90vh] overflow-y-auto bg-e-bg rounded-lg shadow-2xl"
          :style="{ maxWidth: width }"
          role="dialog"
          :aria-label="title"
        >
          <!-- Header -->
          <div class="flex items-center justify-between px-6 py-4 border-b border-e-border">
            <span class="text-lg font-semibold text-e-text">{{ title }}</span>
            <button
              class="text-e-muted hover:text-e-text transition-colors text-xl leading-none cursor-pointer bg-transparent border-none"
              aria-label="Close"
              @click="$emit('update:modelValue', false)"
            >
              ✕
            </button>
          </div>

          <!-- Body -->
          <div class="p-6">
            <slot />
          </div>

          <!-- Footer -->
          <div
            v-if="$slots.footer"
            class="flex justify-end gap-2 px-6 py-4 border-t border-e-border"
          >
            <slot name="footer" />
          </div>
        </div>
      </div>
    </Transition>
  </Teleport>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'EModal',

  props: {
    modelValue:     { type: Boolean, required: true },
    title:          { type: String,  default: '' },
    width:          { type: String,  default: '32rem' },
    closeOnOverlay: { type: Boolean, default: true },
  },

  emits: ['update:modelValue'],

  methods: {
    onOverlayClick() {
      if (this.closeOnOverlay) this.$emit('update:modelValue', false)
    },
  },
})
</script>
