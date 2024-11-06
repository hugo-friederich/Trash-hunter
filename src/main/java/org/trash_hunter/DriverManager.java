package org.trash_hunter;

import java.sql.Connection;
import java.sql.SQLException;

public class DriverManager {
    Connection connexion = java.sql.DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/2024-2025_s1_vs1_tp2_Trash_Hunter","etudiant","YTDTvj9TR3CDYCmP");

    public DriverManager() throws SQLException {
    }
}
