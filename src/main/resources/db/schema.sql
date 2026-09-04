-- =====================================================================
-- MiniERP - PostgreSQL database schema
-- Creates the database and all tables used by the application.
--
-- Run with something like:
--   psql -U postgres -h localhost -f schema.sql
--
-- NOTE: the `minierp` database is created below. If it already exists,
-- drop it first or run the table statements against the existing DB.
-- =====================================================================

DROP DATABASE IF EXISTS minierp;
CREATE DATABASE minierp;
\connect minierp

-- Branches ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS branches (
    branch_id   SERIAL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    address     VARCHAR(255)
);

-- Roles ----------------------------------------------------------------
CREATE TABLE IF NOT EXISTS roles (
    role_id   SERIAL PRIMARY KEY,
    name      VARCHAR(100) NOT NULL
);

-- Employees ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS employees (
    employee_id SERIAL PRIMARY KEY,
    branch_id   INT NOT NULL REFERENCES branches(branch_id),
    role_id     INT NOT NULL REFERENCES roles(role_id),
    name        VARCHAR(100) NOT NULL,
    job_title   VARCHAR(100),
    salary      NUMERIC(12, 2) NOT NULL DEFAULT 0,
    phone       VARCHAR(30),
    hire_date   DATE,
    password    VARCHAR(255),
    status      VARCHAR(20) NOT NULL DEFAULT 'Active'
);

-- Customers ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS customers (
    customer_id SERIAL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    phone       VARCHAR(30)
);

-- Products -------------------------------------------------------------
CREATE TABLE IF NOT EXISTS products (
    product_id      SERIAL PRIMARY KEY,
    name            VARCHAR(150) NOT NULL,
    category        VARCHAR(100),
    barcode         VARCHAR(100),
    purchase_price  NUMERIC(12, 2) NOT NULL DEFAULT 0,
    selling_price   NUMERIC(12, 2) NOT NULL DEFAULT 0
);

-- Warehouses -----------------------------------------------------------
CREATE TABLE IF NOT EXISTS warehouses (
    warehouse_id SERIAL PRIMARY KEY,
    branch_id    INT REFERENCES branches(branch_id),
    name         VARCHAR(100) NOT NULL
);

-- Inventory ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS inventory (
    warehouse_id     INT NOT NULL REFERENCES warehouses(warehouse_id),
    product_id       INT NOT NULL REFERENCES products(product_id),
    quantity         INT NOT NULL DEFAULT 0,
    minimum_quantity INT NOT NULL DEFAULT 0,
    PRIMARY KEY (warehouse_id, product_id)
);

-- Cash registers -------------------------------------------------------
CREATE TABLE IF NOT EXISTS cash_registers (
    cash_register_id SERIAL PRIMARY KEY,
    branch_id        INT NOT NULL REFERENCES branches(branch_id),
    name             VARCHAR(100)
);

-- Sales ----------------------------------------------------------------
CREATE TABLE IF NOT EXISTS sales (
    sale_id          SERIAL PRIMARY KEY,
    branch_id        INT NOT NULL REFERENCES branches(branch_id),
    employee_id      INT NOT NULL REFERENCES employees(employee_id),
    customer_id      INT REFERENCES customers(customer_id),
    cash_register_id INT NOT NULL REFERENCES cash_registers(cash_register_id),
    discount         NUMERIC(12, 2) NOT NULL DEFAULT 0,
    tax              NUMERIC(12, 2) NOT NULL DEFAULT 0,
    total            NUMERIC(12, 2) NOT NULL DEFAULT 0,
    payment_method   VARCHAR(20),
    status           VARCHAR(20) NOT NULL DEFAULT 'Completed',
    sale_date        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Sale items -----------------------------------------------------------
CREATE TABLE IF NOT EXISTS sale_items (
    sale_item_id SERIAL PRIMARY KEY,
    sale_id      INT NOT NULL REFERENCES sales(sale_id),
    product_id   INT NOT NULL REFERENCES products(product_id),
    quantity     INT NOT NULL DEFAULT 0,
    unit_price   NUMERIC(12, 2) NOT NULL DEFAULT 0,
    discount     NUMERIC(12, 2) NOT NULL DEFAULT 0
);