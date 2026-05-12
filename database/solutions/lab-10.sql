BEGIN;
CREATE TABLE triggers_demo (
    id integer PRIMARY KEY,
    dta varchar(255)
);
CREATE SEQUENCE triggers_demo_id_seq START 1;
CREATE FUNCTION set_triggers_demo_id()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.id IS NULL THEN
        NEW.id := nextval('triggers_demo_id_seq');
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER before_insert_triggers_demo
BEFORE INSERT ON triggers_demo
FOR EACH ROW
EXECUTE FUNCTION set_triggers_demo_id();
-- Логи
CREATE TABLE triggers_demo_logs (
    log_id SERIAL PRIMARY KEY,
    operation TEXT,
    changed_at TIMESTAMP,
    changed_by TEXT,
    old_data JSONB,
    new_data JSONB
);
CREATE FUNCTION log_triggers_demo_changes()
RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        INSERT INTO triggers_demo_logs(operation, changed_at, changed_by, new_data)
        VALUES (
            TG_OP,
            NOW(),
            current_user,
            to_jsonb(NEW)
        );
        RETURN NEW;

    ELSIF TG_OP = 'UPDATE' THEN
        INSERT INTO triggers_demo_logs(operation, changed_at, changed_by, old_data, new_data)
        VALUES (
            TG_OP,
            NOW(),
            current_user,
            to_jsonb(OLD),
            to_jsonb(NEW)
        );
        RETURN NEW;

    ELSIF TG_OP = 'DELETE' THEN
        INSERT INTO triggers_demo_logs(operation, changed_at, changed_by, old_data)
        VALUES (
            TG_OP,
            NOW(),
            current_user,
            to_jsonb(OLD)
        );
        RETURN OLD;
    END IF;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER triggers_demo_audit
AFTER INSERT OR UPDATE OR DELETE
ON triggers_demo
FOR EACH ROW
EXECUTE FUNCTION log_triggers_demo_changes();
-- Тест
INSERT INTO triggers_demo (dta)
VALUES ('first row');
INSERT INTO triggers_demo (dta)
VALUES ('second row');
INSERT INTO triggers_demo (dta)
VALUES ('third row');
SELECT * FROM triggers_demo;
SELECT * FROM triggers_demo_logs;
ABORT;