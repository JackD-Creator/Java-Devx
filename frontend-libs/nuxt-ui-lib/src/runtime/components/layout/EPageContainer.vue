<template>
  <main class="e-page">
    <div class="e-page__header" v-if="title || $slots.header">
      <slot name="header">
        <h1 class="e-page__title">{{ title }}</h1>
        <p v-if="subtitle" class="e-page__subtitle">{{ subtitle }}</p>
      </slot>
    </div>
    <div class="e-page__body"><slot /></div>
  </main>
</template>

<script setup lang="ts">
defineProps<{ title?: string; subtitle?: string }>()
</script>

<style scoped>
.e-page         { padding: 1.5rem; max-width: 1280px; margin: 0 auto; }
.e-page__header { margin-bottom: 1.5rem; }
.e-page__title  { font-size: var(--font-size-2xl, 1.5rem); font-weight: var(--font-weight-bold, 700); color: var(--color-text, #111827); margin: 0; }
.e-page__subtitle { font-size: var(--font-size-base, 1rem); color: var(--color-text-muted, #6B7280); margin: 0.25rem 0 0; }
</style>
