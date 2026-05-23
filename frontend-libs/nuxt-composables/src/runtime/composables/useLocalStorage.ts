import { ref, watch } from 'vue'
import type { Ref } from 'vue'

export function useLocalStorage<T>(key: string, defaultValue: T): Ref<T> {
  const stored = typeof window !== 'undefined' ? localStorage.getItem(key) : null
  const data   = ref<T>(stored ? JSON.parse(stored) : defaultValue) as Ref<T>

  watch(data, (val) => {
    if (typeof window === 'undefined') return
    if (val === null || val === undefined) localStorage.removeItem(key)
    else localStorage.setItem(key, JSON.stringify(val))
  }, { deep: true })

  return data
}
