--liquibase formatted sql

--changeset fipe-team:002-add-observations-column
ALTER TABLE vehicle_data ADD COLUMN observations TEXT;
COMMENT ON COLUMN vehicle_data.observations IS 'Custom observations about the vehicle';

--changeset fipe-team:003-add-updated-at-column
ALTER TABLE vehicle_data ADD COLUMN updated_at TIMESTAMP;
UPDATE vehicle_data SET updated_at = created_at WHERE updated_at IS NULL;
ALTER TABLE vehicle_data ALTER COLUMN updated_at SET NOT NULL;
