import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;

public class DatabaseConnectionManagerTest {

    @Test
    public void shouldConnectToDatabase() throws SQLException {
        DriverManager.registerDriver(new org.mariadb.jdbc.Driver());
        Connection connexion = DriverManager.getConnection("jdbc:mariadb://nemrod.ens2m.fr:3306/tp_jdbc", "etudiant", "YTDTvj9TR3CDYCmP");
      
    }
}
