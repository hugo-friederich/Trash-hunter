import org.junit.Assert;
import org.junit.Test;
import org.trash_hunter.util.DatabaseConnectionManager;
import org.trash_hunter.util.OutilsJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManagerTest {

    private Connection connection;
    private void initializeDatabaseConnection() throws SQLException {
        DriverManager.registerDriver(new org.mariadb.jdbc.Driver());
        DatabaseConnectionManager databaseConnectionManager = new DatabaseConnectionManager("nemrod.ens2m.fr:3306", "2024-2025_s1_vs1_tp2_Trash_Hunter", "etudiant", "YTDTvj9TR3CDYCmP");
        connection = databaseConnectionManager.getConnection();
    }
    @Test
    public void shouldConnectToDatabase() throws SQLException {
        initializeDatabaseConnection();
        Assert.assertNotNull(connection);
    }
    @Test
    public void shouldCloseConnectionAfterUse() throws SQLException {
        initializeDatabaseConnection();
        Assert.assertNotNull(connection);
        connection.close();
        Assert.assertTrue(connection.isClosed());
    }
    @Test
    public void shouldPrintTableDiver() throws SQLException {
        initializeDatabaseConnection();
        OutilsJDBC.afficherTable(connection, "Diver");
    }
}
