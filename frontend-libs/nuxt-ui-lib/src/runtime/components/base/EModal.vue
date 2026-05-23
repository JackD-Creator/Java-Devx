<template>
  <Teleport to="body">
    <Transition name="e-modal">
      <div v-if="modelValue" class="e-modal-overlay" @click.self="onOverlayClick">
        <div class="e-modal-box" :style="{ maxWidth: width }" role="dialog" :aria-label="title">
          <div class="e-modal__header">
            <span class="e-modal__title">{{ title }}</span>
            <button class="e-modal__close" aria-label="Close" @click="$emit('update:modelValue', false)">✕</button>
          </div>
          <div class="e-modal__body"><slot /></div>
          <div v-if="$slots.footer" class="e-modal__footer"><slot name="footer" /></div>
        </div>
      </div>
    </Transition>
  </Teleport>
</template>

<script setup lang="ts">
const props = withDefaults(defineProps<{
  modelValue:    boolean
  title?:        string
  width?:        string
  closeOnOverlay?: boolean
}>(), {
  title:           '',
  width:           '32rem',
  closeOnOverlay:  true,
})

const emit = defineEmits<{ 'update:modelValue': [value: boolean] }>()

function onOverlayClick() {
  if (props.closeOnOverlay) emit('update:modelValue', false)
}
</script>

<style scoped>
.e-modal-overlay {
  position: fixed; inset: 0; z-index: 50;
  background: rgba(0,0,0,0.5);
  display: flex; align-items: center; justify-content: center; padding: 1rem;
}
.e-modal-box {
  background: var(--color-background, #fff);
  border-radius: var(--radius-lg, 0.5rem);
  box-shadow: 0 20px 60px rgba(0,0,0,0.2);
  width: 100%; max-height: 90vh; overflow-y: auto;
}
.e-modal__header { display: flex; align-items: center; justify-content: space-between; padding: 1rem 1.5rem; border-bottom: 1px solid var(--color-border, #E5E7EB); }
.e-modal__title  { font-size: var(--font-size-lg, 1.125rem); font-weight: var(--font-weight-semibold, 600); }
.e-modal__close  { background: none; border: none; cursor: pointer; font-size: 1.25rem; color: var(--color-text-muted, #6B7280); }
.e-modal__body   { padding: 1.5rem; }
.e-modal__footer { padding: 1rem 1.5rem; border-top: 1px solid var(--color-border, #E5E7EB); display: flex; justify-content: flex-end; gap: 0.5rem; }
.e-modal-enter-active, .e-modal-leave-active { transition: opacity 0.2s; }
.e-modal-enter-from, .e-modal-leave-to       { opacity: 0; }
</style>
