<template>
  <div class="e-table-wrapper">
    <table class="e-table">
      <thead>
        <tr>
          <th
            v-for="col in columns"
            :key="String(col.key)"
            :style="col.width ? { width: col.width } : {}"
            :class="['e-table__th', `e-table__th--${col.align ?? 'left'}`, { 'e-table__th--sortable': col.sortable }]"
            @click="col.sortable && onSort(String(col.key))"
          >
            {{ col.label }}
            <span v-if="col.sortable && sortKey === String(col.key)">
              {{ sortDir === 'asc' ? '↑' : '↓' }}
            </span>
          </th>
        </tr>
      </thead>
      <tbody>
        <tr v-if="loading">
          <td :colspan="columns.length" class="e-table__empty">Loading...</td>
        </tr>
        <tr v-else-if="!rows.length">
          <td :colspan="columns.length" class="e-table__empty">{{ emptyText }}</td>
        </tr>
        <tr v-for="(row, i) in rows" :key="i" class="e-table__row" @click="$emit('row-click', row)">
          <td
            v-for="col in columns"
            :key="String(col.key)"
            :class="['e-table__td', `e-table__td--${col.align ?? 'left'}`]"
          >
            <slot :name="`cell-${String(col.key)}`" :row="row" :value="row[col.key]">
              {{ col.format ? col.format(row[col.key], row) : row[col.key] }}
            </slot>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup lang="ts" generic="T extends Record<string, unknown>">
import type { TableColumn } from '../../types/ui'

const props = withDefaults(defineProps<{
  columns:    TableColumn<T>[]
  rows:       T[]
  loading?:   boolean
  emptyText?: string
}>(), {
  loading:   false,
  emptyText: 'No data available',
})

defineEmits<{ 'row-click': [row: T] }>()

const sortKey = ref('')
const sortDir = ref<'asc' | 'desc'>('asc')

function onSort(key: string) {
  if (sortKey.value === key) sortDir.value = sortDir.value === 'asc' ? 'desc' : 'asc'
  else { sortKey.value = key; sortDir.value = 'asc' }
}
</script>

<style scoped>
.e-table-wrapper { overflow-x: auto; }
.e-table         { width: 100%; border-collapse: collapse; font-size: var(--font-size-sm, 0.875rem); }
.e-table__th     { padding: 0.75rem 1rem; background: var(--color-surface, #F9FAFB); border-bottom: 1px solid var(--color-border, #E5E7EB); font-weight: var(--font-weight-semibold, 600); white-space: nowrap; }
.e-table__th--sortable { cursor: pointer; user-select: none; }
.e-table__td     { padding: 0.75rem 1rem; border-bottom: 1px solid var(--color-border, #E5E7EB); color: var(--color-text, #111827); }
.e-table__th--left, .e-table__td--left   { text-align: left; }
.e-table__th--center, .e-table__td--center { text-align: center; }
.e-table__th--right, .e-table__td--right { text-align: right; }
.e-table__row:hover { background: var(--color-surface, #F9FAFB); cursor: pointer; }
.e-table__empty  { text-align: center; padding: 2rem; color: var(--color-text-muted, #6B7280); }
</style>
