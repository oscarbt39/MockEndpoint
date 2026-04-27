-- Tabla para guardar los endpoints generados por la IA
CREATE TABLE IF NOT EXISTS endpoints (
    id SERIAL PRIMARY KEY,
    path TEXT NOT NULL,           -- Ej: 'usuarios'
    method TEXT NOT NULL,         -- Ej: 'GET'
    response_body JSONB NOT NULL, -- El JSON que devolverá el mock
    status_code INTEGER DEFAULT 200,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    -- Evitamos duplicados de ruta y método
    UNIQUE(path, method)
);