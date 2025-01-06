package mobs;

import org.junit.Assert;
import org.junit.Test;
import org.trash_hunter.mobs.MobDAO;
import org.trash_hunter.mobs.MobDB;
import org.trash_hunter.user.DiverDB;
import org.trash_hunter.util.DatabaseConnection;
import org.trash_hunter.util.Direction;

import java.sql.Connection;
import java.sql.SQLException;

public class MobDAOTest {
    private MobDAO mobDAO ;
    public void connectionToMobTable() throws SQLException {
        mobDAO = new MobDAO(DatabaseConnection.getConnection());
    }

    @Test
    public void shouldCreateMob () throws SQLException {
        connectionToMobTable();
        MobDB mobDB = new MobDB();
        mobDAO.create(mobDB);
        mobDAO.clear();
    }

    @Test
    public void shouldUpdateMob() throws SQLException{
        connectionToMobTable();
        MobDB newMobDB = new MobDB();
        mobDAO.create(newMobDB);  //creation d'un nouveau diver
        MobDB updatedMobDB = new MobDB();
        updatedMobDB.setDir(Direction.RIGHT);
        mobDAO.update(updatedMobDB, newMobDB.getId());
        mobDAO.delete(newMobDB.getId()); //suppression pour éviter de surcharger la DB
    }

}
