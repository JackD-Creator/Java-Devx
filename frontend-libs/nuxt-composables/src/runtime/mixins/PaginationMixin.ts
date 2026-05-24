import { defineComponent } from 'vue'

/**
 * PaginationMixin — Options API pattern untuk paginasi server-side.
 *
 * Usage:
 *   import { PaginationMixin } from 'nuxt-composables/mixins'
 *   export default defineComponent({
 *     mixins: [PaginationMixin],
 *     methods: { async loadData() { ... this.setTotal(res.total) } }
 *   })
 */
export const PaginationMixin = defineComponent({
  data() {
    return {
      page:      0 as number,
      pageSize:  10 as number,
      total:     0 as number,
    }
  },

  computed: {
    totalPages():  number  { return Math.ceil(this.total / this.pageSize) },
    hasNext():     boolean { return this.page < this.totalPages - 1 },
    hasPrevious(): boolean { return this.page > 0 },
    pageFrom():    number  { return this.total === 0 ? 0 : this.page * this.pageSize + 1 },
    pageTo():      number  { return Math.min((this.page + 1) * this.pageSize, this.total) },
  },

  methods: {
    nextPage()     { if (this.hasNext) this.page++ },
    previousPage() { if (this.hasPrevious) this.page-- },
    goToPage(p: number) {
      this.page = Math.max(0, Math.min(p, this.totalPages - 1))
    },
    resetPage()          { this.page = 0 },
    setTotal(t: number)  { this.total = t },
  },
})
