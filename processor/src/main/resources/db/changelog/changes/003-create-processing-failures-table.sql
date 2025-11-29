CREATE TABLE processing_failures (
    id BIGSERIAL PRIMARY KEY,
    brand_code VARCHAR(50) NOT NULL,
    brand_name VARCHAR(255) NOT NULL,
    failure_reason TEXT,
    stack_trace TEXT,
    retry_count INTEGER NOT NULL DEFAULT 0,
    kafka_topic VARCHAR(255),
    kafka_partition INTEGER,
    kafka_offset BIGINT,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_attempt_at TIMESTAMP
);

CREATE INDEX idx_processing_failures_status ON processing_failures(status);
CREATE INDEX idx_processing_failures_brand_code ON processing_failures(brand_code);
CREATE INDEX idx_processing_failures_created_at ON processing_failures(created_at);
