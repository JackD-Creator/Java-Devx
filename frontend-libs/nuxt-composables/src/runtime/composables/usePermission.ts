import { computed } from 'vue'
import { useState } from '#app'

export interface UserPermissions {
  roles:       string[]
  permissions: string[]
}

export function usePermission() {
  const auth = useState<UserPermissions>('auth:permissions', () => ({
    roles:       [],
    permissions: [],
  }))

  function hasRole(...roles: string[]): boolean {
    return roles.some(r => auth.value.roles.includes(r))
  }

  function hasPermission(...perms: string[]): boolean {
    return perms.some(p => auth.value.permissions.includes(p))
  }

  function hasAll(...perms: string[]): boolean {
    return perms.every(p => auth.value.permissions.includes(p))
  }

  function setPermissions(data: UserPermissions) {
    auth.value = data
  }

  const isAdmin = computed(() => hasRole('admin', 'superadmin'))

  return { hasRole, hasPermission, hasAll, setPermissions, isAdmin, auth }
}
