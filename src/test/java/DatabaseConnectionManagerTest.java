import org.junit.Test;
import org.trash_hunter.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;

public class DatabaseConnectionManagerTest {

    @Test
    public void shouldConnectToDatabase() throws SQLException {

        DatabaseConnectionManager connectionManager = new DatabaseConnectionManager("nemrod.ens2m.fr", "2024-2025_s1_vs1_tp2_Trash_Hunter", "student", "YTDTvj9TR3CDYCmP");
        Connection connection = connectionManager.getConnection();
        assertNotNull(connection);
    }
}
