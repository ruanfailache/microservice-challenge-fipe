-- Create users table with role as enum column
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    last_login_at TIMESTAMP
);

CREATE INDEX idx_username ON users(username);
CREATE INDEX idx_email ON users(email);

-- Insert default admin user
-- Password: admin123
-- Hash generated with PasswordEncoder (SHA-256 with salt)
INSERT INTO users (username, email, password_hash, role, active, created_at, updated_at)
VALUES (
    'admin',
    'admin@fipe.com',
    'nKdP3sG7vM9xR2jL:yUzL7PwQ1fN8tK6jR4vM9xG2cH5sB0nA3dF8lW6yT',
    'ADMIN',
    TRUE,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- Insert default regular user
-- Password: user123
INSERT INTO users (username, email, password_hash, role, active, created_at, updated_at)
VALUES (
    'user',
    'user@fipe.com',
    'mH8qW4tY1nC5xJ9p:rT6kL2gN8wQ3vM5sH7zX0bF4pD1jY9cA6eR8lK3tW',
    'USER',
    TRUE,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

