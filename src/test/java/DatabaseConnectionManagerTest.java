import org.junit.Test;
import org.trash_hunter.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertNotNull;

public class DatabaseConnectionManagerTest {

    @Test
    public void shouldConnectToDatabase() throws SQLException {
        
        Connection connexion = DriverManager.getConnection("jdbc:mariadb://nemrod.ens2m.fr:3306/tp_jdbc", "etudiant", "YTDTvj9TR3CDYCmP");
      
    }
}
