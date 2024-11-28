package util;

import org.junit.Assert;
import org.junit.Test;
import org.trash_hunter.util.DatabaseConnection;
import org.trash_hunter.util.OutilsJDBC;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionTest {

    private Connection connection;
    private void initializeDatabaseConnection() throws SQLException {
        connection = DatabaseConnection.getConnection();
    }

    @Test
    public void shouldConnectToDatabase() throws SQLException {
        initializeDatabaseConnection();
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
