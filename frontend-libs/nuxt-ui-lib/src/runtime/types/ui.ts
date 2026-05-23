export type Size     = 'xs' | 'sm' | 'md' | 'lg' | 'xl'
export type Variant  = 'primary' | 'secondary' | 'accent' | 'ghost' | 'danger'
export type Color    = 'primary' | 'secondary' | 'accent' | 'success' | 'warning' | 'error' | 'info'
export type Align    = 'left' | 'center' | 'right'
export type Position = 'top' | 'bottom' | 'left' | 'right'

export interface TableColumn<T = Record<string, unknown>> {
  key:       keyof T | string
  label:     string
  sortable?: boolean
  align?:    Align
  width?:    string
  format?:   (value: unknown, row: T) => string
}

export interface PaginationMeta {
  page:       number
  size:       number
  total:      number
  totalPages: number
}

export interface SelectOption {
  label: string
  value: string | number | boolean
  disabled?: boolean
}

export interface ToastOptions {
  message:   string
  type?:     Color
  duration?: number
  position?: 'top-right' | 'top-left' | 'bottom-right' | 'bottom-left'
}
