CREATE TABLE vehicle_data (
    id BIGSERIAL PRIMARY KEY,
    brand_code VARCHAR(50) NOT NULL,
    brand_name VARCHAR(255) NOT NULL,
    code VARCHAR(50) NOT NULL,
    model VARCHAR(500) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE vehicle_data ADD CONSTRAINT uk_vehicle_data_brand_code UNIQUE (brand_code, code);

CREATE INDEX idx_vehicle_data_brand ON vehicle_data(brand_code);
CREATE INDEX idx_vehicle_data_created_at ON vehicle_data(created_at);
