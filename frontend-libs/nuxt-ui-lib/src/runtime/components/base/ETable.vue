<template>
  <div class="overflow-x-auto">
    <table class="w-full border-collapse text-sm">
      <thead>
        <tr>
          <th
            v-for="col in columns"
            :key="String(col.key)"
            :style="col.width ? { width: col.width } : {}"
            :class="thClasses(col)"
            @click="col.sortable && onSort(String(col.key))"
          >
            {{ col.label }}
            <span v-if="col.sortable && sortKey === String(col.key)" class="ml-1">
              {{ sortDir === 'asc' ? '↑' : '↓' }}
            </span>
          </th>
        </tr>
      </thead>
      <tbody>
        <tr v-if="loading">
          <td
            :colspan="columns.length"
            class="text-center py-8 text-e-muted"
          >
            <span class="animate-spin inline-block w-5 h-5 border-2 border-primary border-t-transparent rounded-full mr-2" />
            Loading...
          </td>
        </tr>
        <tr v-else-if="!rows.length">
          <td :colspan="columns.length" class="text-center py-8 text-e-muted">
            {{ emptyText }}
          </td>
        </tr>
        <tr
          v-for="(row, i) in rows"
          :key="i"
          class="hover:bg-surface cursor-pointer transition-colors"
          @click="$emit('row-click', row)"
        >
          <td
            v-for="col in columns"
            :key="String(col.key)"
            :class="tdClasses(col)"
          >
            <slot :name="`cell-${String(col.key)}`" :row="row" :value="(row as any)[col.key]">
              {{ col.format ? col.format((row as any)[col.key], row) : (row as any)[col.key] }}
            </slot>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script lang="ts">
import { defineComponent, PropType } from 'vue'
import type { TableColumn } from '../../types/ui'

const ALIGN_CLASSES = {
  left:   'text-left',
  center: 'text-center',
  right:  'text-right',
}

export default defineComponent({
  name: 'ETable',

  props: {
    columns:   { type: Array as PropType<TableColumn<Record<string, unknown>>[]>, required: true },
    rows:      { type: Array as PropType<Record<string, unknown>[]>, required: true },
    loading:   { type: Boolean, default: false },
    emptyText: { type: String,  default: 'No data available' },
  },

  emits: ['row-click'],

  data() {
    return {
      sortKey: '',
      sortDir: 'asc' as 'asc' | 'desc',
    }
  },

  methods: {
    onSort(key: string) {
      if (this.sortKey === key) {
        this.sortDir = this.sortDir === 'asc' ? 'desc' : 'asc'
      } else {
        this.sortKey = key
        this.sortDir = 'asc'
      }
    },

    thClasses(col: TableColumn<Record<string, unknown>>): string {
      return [
        'px-4 py-3 bg-surface border-b border-e-border font-semibold whitespace-nowrap text-e-text',
        ALIGN_CLASSES[col.align ?? 'left'],
        col.sortable ? 'cursor-pointer select-none hover:bg-e-border/20' : '',
      ].filter(Boolean).join(' ')
    },

    tdClasses(col: TableColumn<Record<string, unknown>>): string {
      return [
        'px-4 py-3 border-b border-e-border text-e-text',
        ALIGN_CLASSES[col.align ?? 'left'],
      ].join(' ')
    },
  },
})
</script>
