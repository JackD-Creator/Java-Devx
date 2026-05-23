CREATE TABLE themes (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    slug        VARCHAR(100) NOT NULL UNIQUE,
    active      BOOLEAN NOT NULL DEFAULT FALSE,
    mode        VARCHAR(10) NOT NULL DEFAULT 'light',
    tokens_json TEXT,
    tenant_id   VARCHAR(100),
    created_at  TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_themes_active   ON themes (active);
CREATE INDEX idx_themes_slug     ON themes (slug);
CREATE INDEX idx_themes_tenant   ON themes (tenant_id);

INSERT INTO themes (name, slug, active, mode, tokens_json) VALUES (
    'Default',
    'default',
    TRUE,
    'light',
    '{
      "colors": {
        "primary": "#3B82F6",
        "secondary": "#6B7280",
        "accent": "#8B5CF6",
        "background": "#FFFFFF",
        "surface": "#F9FAFB",
        "text": "#111827",
        "textMuted": "#6B7280",
        "border": "#E5E7EB",
        "error": "#EF4444",
        "success": "#10B981",
        "warning": "#F59E0B",
        "info": "#3B82F6"
      },
      "typography": {
        "fontFamily": "Inter, sans-serif",
        "fontSize": { "xs": "0.75rem", "sm": "0.875rem", "base": "1rem", "lg": "1.125rem", "xl": "1.25rem", "2xl": "1.5rem" },
        "fontWeight": { "normal": "400", "medium": "500", "semibold": "600", "bold": "700" }
      },
      "spacing": { "xs": "0.25rem", "sm": "0.5rem", "md": "1rem", "lg": "1.5rem", "xl": "2rem" },
      "borderRadius": { "sm": "0.25rem", "md": "0.375rem", "lg": "0.5rem", "full": "9999px" }
    }'
);

INSERT INTO themes (name, slug, active, mode, tokens_json) VALUES (
    'Dark',
    'dark',
    FALSE,
    'dark',
    '{
      "colors": {
        "primary": "#60A5FA",
        "secondary": "#9CA3AF",
        "accent": "#A78BFA",
        "background": "#111827",
        "surface": "#1F2937",
        "text": "#F9FAFB",
        "textMuted": "#9CA3AF",
        "border": "#374151",
        "error": "#F87171",
        "success": "#34D399",
        "warning": "#FBBF24",
        "info": "#60A5FA"
      },
      "typography": {
        "fontFamily": "Inter, sans-serif",
        "fontSize": { "xs": "0.75rem", "sm": "0.875rem", "base": "1rem", "lg": "1.125rem", "xl": "1.25rem", "2xl": "1.5rem" },
        "fontWeight": { "normal": "400", "medium": "500", "semibold": "600", "bold": "700" }
      },
      "spacing": { "xs": "0.25rem", "sm": "0.5rem", "md": "1rem", "lg": "1.5rem", "xl": "2rem" },
      "borderRadius": { "sm": "0.25rem", "md": "0.375rem", "lg": "0.5rem", "full": "9999px" }
    }'
);
