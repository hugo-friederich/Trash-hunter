package util;

import org.junit.Test;
import org.trash_hunter.util.DatabaseConnection;
import org.trash_hunter.util.OutilsJDBC;

import java.sql.SQLException;

public class OutilsJDBCTest {
    @Test
    public void shouldShowTable() throws SQLException {
        OutilsJDBC.afficherTable(DatabaseConnection.getConnection(), "Best_scores");
    }
    @Test
    public void shouldShowTableAsString() throws SQLException {
        System.out.println(OutilsJDBC.showTableAsString(DatabaseConnection.getConnection(),"Best_scores"));
    }
}
