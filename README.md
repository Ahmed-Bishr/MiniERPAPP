# Mini ERP (Java + PostgreSQL)

A small menu-driven ERP application (Swing `JOptionPane` UI) that talks to a
**PostgreSQL** database through JDBC.

## Requirements

* JDK 21+ (the project targets Java 21)
* PostgreSQL running locally (default `localhost:5432`)
* Optional: Maven 3.9+ (the project is a standard Maven layout)

## 1. Create the database

The app expects a database named `minierp` (configurable). Create it and all
tables with the provided script:

```bash
psql -U postgres -h localhost -f src/main/resources/db/schema.sql
```

## 2. Configure the connection

The connection settings are hard-coded as constants in
`src/main/java/org/example/config/DatabaseConnector.java`. Edit them to match
your local PostgreSQL setup before running:

```java
private static final String HOST     = "localhost";
private static final int    PORT     = 5432;
private static final String DATABASE = "minierp"; // created by db/schema.sql
private static final String USER     = "postgres";
private static final String PASSWORD = "1234";    // your PostgreSQL password
```

## 3. Build & run

### With Maven

```bash
mvn -q compile
mvn -q exec:java -Dexec.mainClass=org.example.Main   # requires exec plugin
# or simply run org.example.Main from your IDE
```

### Without Maven (javac)

The PostgreSQL JDBC driver is expected in `lib/` (see below).

```bash
chmod +x build.sh run.sh
./build.sh     # compile only
./run.sh       # compile + run
```

> **lib / driver jar:** if Maven is not installed, download the driver once:

```bash
mkdir -p lib
curl -fL -o lib/postgresql-42.7.4.jar \
  https://repo.maven.apache.org/maven2/org/postgresql/postgresql/42.7.4/postgresql-42.7.4.jar
```

## Project layout

Each feature is a small layered module: `*Menu` (Swing dialogs) → `*Service`
(logic) → `*Repository` (SQL/JDBC) → DTO (data) → PostgreSQL.

```
src/main/java/org/example/
    Main.java                 entry point: wires repositories/services/menus
    MainMenu.java             top-level menu (1..7) delegating to modules
    config/
        DatabaseConnector.java   PostgreSQL JDBC connection (hard-coded settings)
    common/
        DatabaseErrorHandler.java  central "SQL Error:" dialog helper
    dto/
        ProductDTO.java  CustomerDTO.java  EmployeeDTO.java
        InventoryDTO.java  SaleDTO.java  SaleItemDTO.java
    product/
        ProductRepository.java   SQL for products
        ProductService.java      product logic
        ProductMenu.java         product JOptionPane UI
    customer/
        CustomerRepository.java  CustomerService.java  CustomerMenu.java
    employee/
        EmployeeRepository.java  EmployeeService.java  EmployeeMenu.java
    inventory/
        InventoryRepository.java  InventoryService.java  InventoryMenu.java
    sale/
        SaleRepository.java       SQL incl. the atomic create-sale transaction
        SaleService.java          sale logic
        SaleMenu.java             create-sale / view-sales UI
        CreateSaleResult.java     transaction result holder
        CreateSaleStatus.java     transaction outcome enum
src/main/resources/
    db/schema.sql             PostgreSQL schema (tables + keys)
```

## Notes

* Every `DatabaseConnector.connect()` call opens a fresh connection;
  callers are responsible for closing it.
* "Firing" an employee updates `status` to `Fired` (row is kept for
  sales/purchases referential integrity).
* Creating a sale runs as a single atomic transaction in
  `SaleRepository.createSale(...)`: read price → check stock → insert sale →
  read generated id → insert sale item → reduce inventory → commit (rolls
  back entirely on any problem).