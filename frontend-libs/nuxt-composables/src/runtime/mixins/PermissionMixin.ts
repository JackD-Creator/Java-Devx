import { defineComponent } from 'vue'

export interface UserAuth {
  roles:       string[]
  permissions: string[]
}

/**
 * PermissionMixin — Options API pattern untuk role-based access control.
 *
 * Usage:
 *   import { PermissionMixin } from 'nuxt-composables/mixins'
 *   export default defineComponent({
 *     mixins: [PermissionMixin],
 *     mounted() { this.setAuth({ roles: ['admin'], permissions: ['read', 'write'] }) },
 *     computed: {
 *       canEdit() { return this.hasPermission('write') }
 *     }
 *   })
 */
export const PermissionMixin = defineComponent({
  data() {
    return {
      auth: {
        roles:       [] as string[],
        permissions: [] as string[],
      } as UserAuth,
    }
  },

  computed: {
    isAdmin(): boolean {
      return this.hasRole('admin', 'superadmin')
    },
  },

  methods: {
    setAuth(data: UserAuth) {
      this.auth = data
    },

    hasRole(...roles: string[]): boolean {
      return roles.some(r => this.auth.roles.includes(r))
    },

    hasPermission(...perms: string[]): boolean {
      return perms.some(p => this.auth.permissions.includes(p))
    },

    hasAllPermissions(...perms: string[]): boolean {
      return perms.every(p => this.auth.permissions.includes(p))
    },
  },
})
