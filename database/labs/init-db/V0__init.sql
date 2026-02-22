CREATE SCHEMA IF NOT EXISTS hr;

-- Регион
CREATE TABLE hr.regions (
    region_id integer PRIMARY KEY,
    region_name character varying(25) NOT NULL
);
-- Страна
CREATE TABLE hr.countries (
    country_id character(2) PRIMARY KEY,
    country_name character varying(40) NOT NULL,
    region_id integer NOT NULL,
    CONSTRAINT countries_region_id_fk FOREIGN KEY (region_id) REFERENCES hr.regions(region_id) ON UPDATE CASCADE ON DELETE CASCADE
);
-- Местположение
CREATE TABLE hr.locations (
    location_id integer NOT NULL,
    street_address character varying(40),
    postal_code character varying(12),
    city character varying(30) NOT NULL,
    state_province character varying(25),
    country_id character(2) NOT NULL
);
ALTER TABLE hr.locations
ADD CONSTRAINT locations_pk PRIMARY KEY (location_id);
ALTER TABLE hr.locations
ADD CONSTRAINT locations_country_id_fk FOREIGN KEY (country_id) REFERENCES hr.countries(country_id) ON UPDATE CASCADE ON DELETE CASCADE;
-- Отдел
CREATE TABLE hr.departments (
    department_id integer NOT NULL PRIMARY KEY,
    department_name character varying(30) NOT NULL,
    location_id integer,
    CONSTRAINT departments_location_id_fk FOREIGN KEY (location_id) REFERENCES hr.locations(location_id) ON UPDATE CASCADE ON DELETE CASCADE
);
-- Должность
CREATE TABLE hr.jobs (
    job_id integer NOT NULL PRIMARY KEY,
    job_title character varying(35) NOT NULL,
    min_salary numeric(8, 2),
    max_salary numeric(8, 2)
);
-- Сотрудники
CREATE TABLE hr.employees (
    employee_id integer NOT NULL PRIMARY KEY,
    first_name character varying(20),
    last_name character varying(25) NOT NULL,
    email character varying(100) NOT NULL,
    phone_number character varying(20),
    hire_date date NOT NULL,
    job_id integer NOT NULL,
    salary numeric(8, 2) NOT NULL,
    manager_id integer,
    department_id integer,
    CONSTRAINT employees_department_id_fk FOREIGN KEY (department_id) REFERENCES hr.departments(department_id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT employees_job_id_fk FOREIGN KEY (job_id) REFERENCES hr.jobs(job_id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT employees_manager_id_fk FOREIGN KEY (manager_id) REFERENCES hr.employees(employee_id) ON UPDATE CASCADE ON DELETE CASCADE
);
-- Иждивенец
CREATE TABLE hr.dependents (
    dependent_id integer NOT NULL PRIMARY KEY,
    first_name character varying(50) NOT NULL,
    last_name character varying(50) NOT NULL,
    relationship character varying(25) NOT NULL,
    employee_id integer NOT NULL,
    CONSTRAINT dependents_employee_id_fk FOREIGN KEY (employee_id) REFERENCES hr.employees(employee_id) ON UPDATE CASCADE ON DELETE CASCADE
);