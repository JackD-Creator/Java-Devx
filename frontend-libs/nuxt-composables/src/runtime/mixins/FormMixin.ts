import { defineComponent } from 'vue'

type RuleFn  = (value: unknown) => string | true
type RuleMap = Record<string, RuleFn[]>

/**
 * FormMixin — Options API pattern untuk form validation.
 *
 * Usage:
 *   import { FormMixin } from 'nuxt-composables/mixins'
 *   export default defineComponent({
 *     mixins: [FormMixin],
 *     data() {
 *       return {
 *         formValues: { email: '', password: '' },
 *         formRules: {
 *           email: [v => !!v || 'Email wajib diisi'],
 *           password: [v => String(v).length >= 8 || 'Min 8 karakter'],
 *         },
 *       }
 *     },
 *     methods: {
 *       async submit() {
 *         await this.formSubmit(async () => { ... })
 *       }
 *     }
 *   })
 */
export const FormMixin = defineComponent({
  data() {
    return {
      formValues:  {} as Record<string, unknown>,
      formErrors:  {} as Record<string, string>,
      formTouched: {} as Record<string, boolean>,
      formLoading: false as boolean,
      formRules:   {} as RuleMap,
    }
  },

  computed: {
    formIsValid(): boolean {
      return Object.keys(this.formErrors).length === 0
    },
  },

  methods: {
    formValidate(): boolean {
      let valid = true
      for (const field in this.formRules) {
        const rules = this.formRules[field] ?? []
        let fieldValid = true
        for (const rule of rules) {
          const result = rule(this.formValues[field])
          if (result !== true) {
            this.formErrors[field] = result as string
            fieldValid = false
            valid = false
            break
          }
        }
        if (fieldValid) delete this.formErrors[field]
      }
      return valid
    },

    formTouch(field: string) {
      this.formTouched[field] = true
    },

    formReset() {
      this.formErrors  = {}
      this.formTouched = {}
    },

    async formSubmit(fn: (values: Record<string, unknown>) => Promise<void>) {
      if (!this.formValidate()) return
      this.formLoading = true
      try {
        await fn(this.formValues)
      } finally {
        this.formLoading = false
      }
    },
  },
})
