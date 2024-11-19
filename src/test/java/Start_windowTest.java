import org.junit.Test;
import org.trash_hunter.util.DatabaseConnection;
import org.trash_hunter.windows.Start_window;

import java.sql.Connection;
import java.sql.SQLException;

public class Start_windowTest {
    private Connection connection;
    private void initializeDatabaseConnection() throws SQLException {
        connection = DatabaseConnection.getConnection();
    }
    @Test
    public void shouldDeclineUsedPseudo() throws SQLException{
        new Start_window();
    }
}
