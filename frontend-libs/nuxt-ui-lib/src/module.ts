import { defineNuxtModule, addComponent, createResolver } from '@nuxt/kit'

export interface ModuleOptions {
  prefix?: string
}

export default defineNuxtModule<ModuleOptions>({
  meta: {
    name: 'nuxt-ui-lib',
    configKey: 'uiLib',
    compatibility: { nuxt: '>=3.0.0' },
  },
  defaults: { prefix: 'E' },

  setup(_options, _nuxt) {
    const resolver = createResolver(import.meta.url)

    const components = [
      'base/EButton',
      'base/EInput',
      'base/ETable',
      'base/EModal',
      'base/EBadge',
      'layout/EPageContainer',
    ]

    for (const comp of components) {
      const name = comp.split('/').pop()!
      addComponent({
        name,
        filePath: resolver.resolve(`./runtime/components/${comp}.vue`),
      })
    }
  },
})
