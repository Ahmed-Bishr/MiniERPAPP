package org.example.common;

import javax.swing.JOptionPane;
import java.sql.SQLException;


public final class DatabaseErrorHandler {

    public static void showError(SQLException ex) {
        JOptionPane.showMessageDialog(
                null,
                "SQL Error:\n" + ex.getMessage()
        );
    }
}