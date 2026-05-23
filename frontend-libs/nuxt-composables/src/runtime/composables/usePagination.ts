import { ref, computed } from 'vue'

export function usePagination(initialSize = 10) {
  const page  = ref(0)
  const size  = ref(initialSize)
  const total = ref(0)

  const totalPages  = computed(() => Math.ceil(total.value / size.value))
  const hasNext     = computed(() => page.value < totalPages.value - 1)
  const hasPrevious = computed(() => page.value > 0)
  const from        = computed(() => page.value * size.value + 1)
  const to          = computed(() => Math.min((page.value + 1) * size.value, total.value))

  function next()     { if (hasNext.value) page.value++ }
  function previous() { if (hasPrevious.value) page.value-- }
  function goTo(p: number) { page.value = Math.max(0, Math.min(p, totalPages.value - 1)) }
  function reset()    { page.value = 0 }
  function setTotal(t: number) { total.value = t }

  return { page, size, total, totalPages, hasNext, hasPrevious, from, to, next, previous, goTo, reset, setTotal }
}
