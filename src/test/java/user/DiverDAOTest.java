package user;

import org.junit.Test;
import org.trash_hunter.user.DiverDB;
import org.trash_hunter.user.DiverDAO;
import org.trash_hunter.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class DiverDAOTest {
    private Connection connection;
    private void initializeDatabaseConnection() throws SQLException {
        connection = DatabaseConnection.getConnection();
    }
    // Doit créer un Diver nommé Toto de couleur rouge dans la base de donnée
    @Test
    public void shouldCreateDiver () throws SQLException{
        initializeDatabaseConnection();
        DiverDAO diverDAO = new DiverDAO(connection);
        DiverDB newDiverDB = new DiverDB("toto","red");
        diverDAO.create(newDiverDB);
        diverDAO.delete(newDiverDB.getId());  //suppression pour éviter de surcharger la DB
    }

    //Doit créer un Diver puis le mettre à jour à l'aide d'un nouveau Diver
    @Test
    public void shouldUpdateDiver () throws SQLException{
        initializeDatabaseConnection();
        DiverDAO diverDAO = new DiverDAO(connection);
        DiverDB newDiverDB = new DiverDB("toto","red");
        diverDAO.create(newDiverDB);  //creation d'un nouveau diver
        DiverDB updatedDiverDB = new DiverDB("titi","blue");
        diverDAO.update(updatedDiverDB, newDiverDB.getId());
        diverDAO.delete(newDiverDB.getId()); //suppression pour éviter de surcharger la DB
    }
    // Doit créer un nouveau Diver et le supprimer juste après
    @Test
    public void shouldDeleteDiver () throws SQLException {
        initializeDatabaseConnection();
        DiverDAO diverDAO = new DiverDAO(connection);
        DiverDB newDiverDB = new DiverDB("toto", "red");
        diverDAO.create(newDiverDB);  //creation d'un nouveau diver
        diverDAO.delete(newDiverDB.getId());
    }
    @Test
    public void shouldFindAll () throws SQLException{
        initializeDatabaseConnection();
        DiverDAO diverDAO = new DiverDAO(connection);
        System.out.print(diverDAO.findAll().toString());
    }
    @Test
    public void shouldFindAllPseudo () throws SQLException{
        initializeDatabaseConnection();
        DiverDAO diverDAO=new DiverDAO(connection);
        System.out.print(diverDAO.findAllPseudoFromBestScore());
    }

    @Test
    public void shouldGetAllPseudoFromDiver () throws SQLException {
        initializeDatabaseConnection();
        DiverDAO diverDAO=new DiverDAO(connection);
        System.out.println(diverDAO.findAllPseudoFromDiver());
    }
}
