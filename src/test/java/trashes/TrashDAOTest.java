package trashes;

import org.junit.Assert;
import org.junit.Test;
import org.trash_hunter.Game;
import org.trash_hunter.trashes.Bottle;
import org.trash_hunter.trashes.Trash;
import org.trash_hunter.trashes.TrashDAO;
import org.trash_hunter.user.Diver;
import org.trash_hunter.util.DatabaseConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        bottle1.setX(10);
        Bottle bottle2 = new Bottle(2);
        bottle2.setX(100);
        trashDAO.create(bottle1);
        trashDAO.update(bottle2,2);
        Assert.assertEquals(100,bottle2.getX(),0);
    }
    @Test
    public void shouldSelectTrash() throws SQLException {
        TrashDAO trashDAO = new TrashDAO(DatabaseConnection.getConnection());
        trashDAO.create(new Bottle());
        List <Trash> trashset = trashDAO.findAll();
    }
    public static void main (String[]args){
        Diver diver = new Diver();
        diver.setPseudo("hugo");
        System.out.println(diver.toString());
    }
}
