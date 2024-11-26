package trashes;

import org.junit.Assert;
import org.junit.Test;
import org.trash_hunter.Game;
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

    /**
     * Doit initialiser les déchets en local et dans la base de donnée
     * @throws SQLException
     */
    @Test
    public void shouldInitTrashes () throws  SQLException{
        TrashDAO trashDAO = new TrashDAO(DatabaseConnection.getConnection());
        Game game = new Game();
        Assert.assertEquals(game.getLocalTrashset().toString(),trashDAO.findAll().toString());
    }
    @Test
    public void shouldUpdateATrash() throws SQLException {
        TrashDAO trashDAO = new TrashDAO(DatabaseConnection.getConnection());
        trashDAO.clear();
        Bottle bottle1 = new Bottle(2);
        Bottle bottle2 = new Bottle(2);
        trashDAO.create(bottle1);
        trashDAO.update(bottle2,2);
    }
}
