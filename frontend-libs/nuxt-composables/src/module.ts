import { defineNuxtModule, addImportsDir, createResolver } from '@nuxt/kit'

export default defineNuxtModule({
  meta: {
    name: 'nuxt-composables',
    configKey: 'composablesLib',
    compatibility: { nuxt: '>=3.0.0' },
  },
  setup(_options, _nuxt) {
    const resolver = createResolver(import.meta.url)
    addImportsDir(resolver.resolve('./runtime/composables'))
  },
})
