-- Tabel contoh untuk demo DB Adapter
-- Modul yang menggunakan adapter ini mendefinisikan tabel mereka sendiri

CREATE TABLE IF NOT EXISTS schema_info (
    id          BIGSERIAL PRIMARY KEY,
    version     VARCHAR(20) NOT NULL,
    description VARCHAR(255),
    created_at  TIMESTAMP NOT NULL DEFAULT NOW()
);

INSERT INTO schema_info (version, description)
VALUES ('1.0.0', 'Initial schema by quarkus-db-adapter');
