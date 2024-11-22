package trashes;

import org.junit.Assert;
import org.junit.Test;
import org.trash_hunter.Game;
import org.trash_hunter.trashes.Bottle;
import org.trash_hunter.trashes.Trash;
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
    @Test
    public void shouldShowAllTrashes () throws  SQLException{
        TrashDAO trashDAO = new TrashDAO(DatabaseConnection.getConnection());
        Game game = new Game();
        game.initTrashes();
        for (Trash trash : game.getTrashset()){
            Assert.assertNotNull(trash.getSprite());
        }
    }
}
