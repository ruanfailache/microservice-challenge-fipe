ALTER TABLE vehicle_data ADD COLUMN observations TEXT;

ALTER TABLE vehicle_data ADD COLUMN updated_at TIMESTAMP;
UPDATE vehicle_data SET updated_at = created_at WHERE updated_at IS NULL;
ALTER TABLE vehicle_data ALTER COLUMN updated_at SET NOT NULL;
