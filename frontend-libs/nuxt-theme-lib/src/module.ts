import { defineNuxtModule, addPlugin, addImportsDir, createResolver } from '@nuxt/kit'

export interface ModuleOptions {
  apiBaseUrl: string
  cacheTtl: number
  defaultTheme: string
}

export default defineNuxtModule<ModuleOptions>({
  meta: {
    name: 'nuxt-theme-lib',
    configKey: 'themeLib',
    compatibility: { nuxt: '>=3.0.0' },
  },

  defaults: {
    apiBaseUrl:   process.env.THEME_API_BASE_URL || 'http://localhost:8080',
    cacheTtl:     300,
    defaultTheme: 'default',
  },

  setup(options, nuxt) {
    const resolver = createResolver(import.meta.url)

    nuxt.options.runtimeConfig.public.themeLib = {
      apiBaseUrl:   options.apiBaseUrl,
      cacheTtl:     options.cacheTtl,
      defaultTheme: options.defaultTheme,
    }

    addPlugin(resolver.resolve('./runtime/plugins/theme'))
    addImportsDir(resolver.resolve('./runtime/composables'))
  },
})
