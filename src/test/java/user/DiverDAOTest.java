package user;

import org.junit.Test;
import org.trash_hunter.user.Diver;
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
        Diver newDiver = new Diver("toto","red");
        diverDAO.create(newDiver);
        diverDAO.delete(newDiver.getId());  //suppression pour éviter de surcharger la DB
    }

    //Doit créer un Diver puis le mettre à jour à l'aide d'un nouveau Diver
    @Test
    public void shouldUpdateDiver () throws SQLException{
        initializeDatabaseConnection();
        DiverDAO diverDAO = new DiverDAO(connection);
        Diver newDiver = new Diver("toto","red");
        diverDAO.create(newDiver);  //creation d'un nouveau diver
        Diver updatedDiver = new Diver("titi","blue");
        diverDAO.update(updatedDiver, newDiver.getId());
        diverDAO.delete(newDiver.getId()); //suppression pour éviter de surcharger la DB
    }
    // Doit créer un nouveau Diver et le supprimer juste après
    @Test
    public void shouldDeleteDiver () throws SQLException {
        initializeDatabaseConnection();
        DiverDAO diverDAO = new DiverDAO(connection);
        Diver newDiver = new Diver("toto", "red");
        diverDAO.create(newDiver);  //creation d'un nouveau diver
        diverDAO.delete(newDiver.getId());
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
