export interface ThemeColors {
  primary: string
  secondary: string
  accent: string
  background: string
  surface: string
  text: string
  textMuted: string
  border: string
  error: string
  success: string
  warning: string
  info: string
}

export interface ThemeTypography {
  fontFamily: string
  fontSize: {
    xs: string
    sm: string
    base: string
    lg: string
    xl: string
    '2xl': string
  }
  fontWeight: {
    normal: string
    medium: string
    semibold: string
    bold: string
  }
}

export interface ThemeSpacing {
  xs: string
  sm: string
  md: string
  lg: string
  xl: string
}

export interface ThemeBorderRadius {
  sm: string
  md: string
  lg: string
  full: string
}

export interface ThemeTokens {
  colors: ThemeColors
  typography: ThemeTypography
  spacing: ThemeSpacing
  borderRadius: ThemeBorderRadius
}

export interface Theme {
  id: number | string
  name: string
  slug: string
  active: boolean
  mode: 'light' | 'dark'
  tokensJson: string
  tenantId?: string
  createdAt?: string
  updatedAt?: string
}

export interface ThemeResolved extends Omit<Theme, 'tokensJson'> {
  tokens: ThemeTokens
}
