package trashes;

import org.junit.Test;
import org.trash_hunter.trashes.Bottle;
import org.trash_hunter.trashes.TrashDAO;
import org.trash_hunter.util.DatabaseConnection;

import java.sql.SQLException;

public class TrashDAOTest {
    @Test
    public void shouldClearTrashes() throws SQLException {
        TrashDAO trashDAO = new TrashDAO(DatabaseConnection.getConnection());
        Bottle newBottle = new Bottle();
        trashDAO.create(newBottle);
        trashDAO.clear();
    }
}
