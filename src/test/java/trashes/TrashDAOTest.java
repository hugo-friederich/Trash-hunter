package trashes;

import org.junit.Assert;
import org.junit.Test;
import org.trash_hunter.Game;
import org.trash_hunter.trashes.Bottle;
import org.trash_hunter.trashes.Trash;
import org.trash_hunter.trashes.TrashDAO;
import org.trash_hunter.trashes.TrashDB;
import org.trash_hunter.user.Diver;
import org.trash_hunter.user.DiverDAO;
import org.trash_hunter.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrashDAOTest {
    private Connection connection;
    private TrashDAO trashDAO;
    private void initializeDatabaseConnection() throws SQLException {
        connection = DatabaseConnection.getConnection();
        this.trashDAO= new TrashDAO(connection);
    }
    // Doit créer un Diver nommé Toto de couleur rouge dans la base de donnée
    @Test
    public void shouldDeleteTrash () throws SQLException{
        initializeDatabaseConnection();
        trashDAO.clear();
        TrashDB newtrashDB = new TrashDB();
        trashDAO.create(newtrashDB);
        trashDAO.delete(newtrashDB.getId());  //suppression pour éviter de surcharger la DB
    }

    //Doit créer un Diver puis le mettre à jour à l'aide d'un nouveau Diver
    @Test
    public void shouldUpdateTrash () throws SQLException{
        initializeDatabaseConnection();
        trashDAO.clear();
        TrashDB newTrashDB = new TrashDB();
        trashDAO.create(newTrashDB);  //creation d'un nouveau diver
        TrashDB updatedTrashDB = new TrashDB();
        updatedTrashDB.setVisible(0);
        trashDAO.update(updatedTrashDB, newTrashDB.getId());
        trashDAO.delete(newTrashDB.getId()); //suppression pour éviter de surcharger la DB
    }
    @Test
    public void shouldFindAll () throws SQLException{
        initializeDatabaseConnection();
        trashDAO.create(new TrashDB("bottle"));
        trashDAO.create(new TrashDB("can"));
        trashDAO.create(new TrashDB("boat"));
        System.out.print(trashDAO.findAll().toString());
        trashDAO.clear();
    }
    @Test
    public void shouldFindAllName () throws SQLException{
        initializeDatabaseConnection();
        System.out.print(trashDAO.findAllNames());
    }
    @Test
    public void shouldClearTrashes()throws  SQLException{
        initializeDatabaseConnection();
        trashDAO.clear();
    }
}
