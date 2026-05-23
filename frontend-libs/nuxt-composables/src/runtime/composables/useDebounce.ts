import { ref, watch } from 'vue'
import type { Ref } from 'vue'

export function useDebounce<T>(source: Ref<T>, delay = 300): Ref<T> {
  const debounced = ref<T>(source.value) as Ref<T>
  let timer: ReturnType<typeof setTimeout>

  watch(source, (val) => {
    clearTimeout(timer)
    timer = setTimeout(() => { debounced.value = val }, delay)
  })

  return debounced
}

export function useThrottle<T>(source: Ref<T>, interval = 300): Ref<T> {
  const throttled = ref<T>(source.value) as Ref<T>
  let last = 0

  watch(source, (val) => {
    const now = Date.now()
    if (now - last >= interval) {
      last = now
      throttled.value = val
    }
  })

  return throttled
}
