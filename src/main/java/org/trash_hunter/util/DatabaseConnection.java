package org.trash_hunter.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mariadb://nemrod.ens2m.fr:3306/2024-2025_s1_vs1_tp2_Trash_Hunter");
        config.setUsername("etudiant");
        config.setPassword("YTDTvj9TR3CDYCmP");
        config.setMaximumPoolSize(6); // Nombre maximum de connexions dans le pool
        config.setConnectionTimeout(30000); // Temps d'attente pour obtenir une connexion
        config.setIdleTimeout(600000); // Temps d'inactivité avant qu'une connexion soit fermée
        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void close() {
        dataSource.close();
    }

}