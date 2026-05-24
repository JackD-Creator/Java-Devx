<template>
  <main class="px-6 py-6 max-w-7xl mx-auto">
    <div v-if="title || $slots.header" class="mb-6">
      <slot name="header">
        <div class="flex items-start justify-between gap-4">
          <div>
            <h1 class="text-2xl font-bold text-e-text m-0">{{ title }}</h1>
            <p v-if="subtitle" class="text-base text-e-muted mt-1 m-0">{{ subtitle }}</p>
          </div>
          <div v-if="$slots.actions" class="flex-shrink-0">
            <slot name="actions" />
          </div>
        </div>
      </slot>
    </div>
    <div>
      <slot />
    </div>
  </main>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'EPageContainer',

  props: {
    title:    { type: String, default: '' },
    subtitle: { type: String, default: '' },
  },
})
</script>
