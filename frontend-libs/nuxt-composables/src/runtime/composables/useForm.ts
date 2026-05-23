import { ref, reactive, computed } from 'vue'

type Rules<T> = Partial<Record<keyof T, ((v: unknown) => string | true)[]>>
type Errors<T> = Partial<Record<keyof T, string>>

export function useForm<T extends Record<string, unknown>>(initialValues: T, rules: Rules<T> = {}) {
  const values  = reactive<T>({ ...initialValues })
  const errors  = reactive<Errors<T>>({})
  const touched = reactive<Partial<Record<keyof T, boolean>>>({})
  const loading = ref(false)

  const isValid = computed(() => Object.keys(errors).length === 0)

  function validate(): boolean {
    let valid = true
    for (const field in rules) {
      const fieldRules = rules[field as keyof T] ?? []
      for (const rule of fieldRules) {
        const result = rule(values[field as keyof T])
        if (result !== true) {
          errors[field as keyof T] = result
          valid = false
          break
        } else {
          delete errors[field as keyof T]
        }
      }
    }
    return valid
  }

  function touch(field: keyof T) {
    touched[field] = true
  }

  function reset() {
    Object.assign(values, initialValues)
    Object.keys(errors).forEach(k => delete errors[k as keyof T])
    Object.keys(touched).forEach(k => delete touched[k as keyof T])
  }

  async function handleSubmit(fn: (values: T) => Promise<void>) {
    if (!validate()) return
    loading.value = true
    try { await fn(values as T) }
    finally { loading.value = false }
  }

  return { values, errors, touched, loading, isValid, validate, touch, reset, handleSubmit }
}
