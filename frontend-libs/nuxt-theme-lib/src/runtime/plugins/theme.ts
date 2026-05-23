import { defineNuxtPlugin } from '#app'
import { useTheme } from '../composables/useTheme'

export default defineNuxtPlugin(async () => {
  const { fetchActiveTheme } = useTheme()
  await fetchActiveTheme()
})
