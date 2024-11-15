import org.junit.Assert;
import org.junit.Test;
import org.trash_hunter.Diver;
import org.trash_hunter.DiverDAO;
import org.trash_hunter.util.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DiverDAOTest {
    private Connection connection;
    private void initializeDatabaseConnection() throws SQLException {
        DriverManager.registerDriver(new org.mariadb.jdbc.Driver());
        DatabaseConnectionManager databaseConnectionManager = new DatabaseConnectionManager("nemrod.ens2m.fr:3306", "2024-2025_s1_vs1_tp2_Trash_Hunter", "etudiant", "YTDTvj9TR3CDYCmP");
        connection = databaseConnectionManager.getConnection();
    }

    @Test
    public void shouldSelectDiverById () throws SQLException{
        initializeDatabaseConnection();
        DiverDAO diverSerched = new DiverDAO(connection);
        Diver diver = diverSerched.findById(1);
        System.out.print(diver.toString());
        Assert.assertEquals(diver.getId(),1);
        Assert.assertEquals(diver.getPseudo(),"'Hugo'");
    }

    // a compléter
    @Test
    public void shouldCreateDiver () throws SQLException{
        initializeDatabaseConnection();
        DiverDAO diverDAO = new DiverDAO(connection);
        Diver newDiver = new Diver();
    }
}
