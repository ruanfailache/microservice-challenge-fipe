--liquibase formatted sql

--changeset fipe-team:001-create-brand-table
CREATE TABLE brand (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_brand_code ON brand(code);
CREATE INDEX idx_brand_name ON brand(name);

--changeset fipe-team:002-create-vehicle-table
CREATE TABLE vehicle (
    id BIGSERIAL PRIMARY KEY,
    brand_code VARCHAR(50) NOT NULL,
    brand_name VARCHAR(255) NOT NULL,
    code VARCHAR(50) NOT NULL,
    model VARCHAR(500) NOT NULL,
    observations TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_vehicle_brand_code UNIQUE (brand_code, code)
);

CREATE INDEX idx_vehicle_brand_code ON vehicle(brand_code);
CREATE INDEX idx_vehicle_created_at ON vehicle(created_at);
