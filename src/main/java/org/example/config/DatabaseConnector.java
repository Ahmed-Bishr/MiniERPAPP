package org.example.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides JDBC connections to the PostgreSQL database used by MiniERP.
 *
 * <p>On every call {@link #connect()} opens a fresh connection and returns it
 * already usable. The caller is responsible for closing it (and rolling back
 * in case of error). Connect settings are hard-coded below for simplicity.</p>
 */
public final class DatabaseConnector {

    private static final String HOST = "localhost";
    private static final int PORT = 5432;
    private static final String DATABASE = "minierp";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    private static final String URL =
            "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DATABASE;

    /**
     * Opens and returns a PostgreSQL connection.
     *
     * <p>Throws a {@link SQLException} when the server is unreachable or the
     * credentials are wrong. Callers should let this propagate so existing
     * {@code try}/{@code catch (SQLException ...)} blocks (and transaction
     * rollbacks) work correctly.</p>
     *
     * @return an open {@link Connection}
     * @throws SQLException if the driver is missing or the connection fails
     */
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Simple connection smoke test:
     * {@code java org.example.config.DatabaseConnector}
     */
    public static void main(String[] args) {
        try (Connection connection = connect()) {
            System.out.println("Connected to PostgreSQL successfully!");
        } catch (SQLException e) {
            System.err.println("Connection failed!");
            e.printStackTrace();
        }
    }
}