package user;

import org.junit.Test;
import org.trash_hunter.user.Avatar;
import org.trash_hunter.user.DiverDAO;
import org.trash_hunter.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class DiverDAOTest {
    private Connection connection;
    private void initializeDatabaseConnection() throws SQLException {
        connection = DatabaseConnection.getConnection();
    }

    @Test
    public void shouldSelectDiverById () throws SQLException{
        initializeDatabaseConnection();
        DiverDAO diverSerched = new DiverDAO(connection);
        Avatar newAvatar = new Avatar();
        diverSerched.create(newAvatar);
        Avatar avatar = diverSerched.findById(newAvatar.getId());
        System.out.print(avatar.toString());
        diverSerched.delete(newAvatar.getId());
    }

    // Doit créer un Diver nommé Toto de couleur rouge dans la base de donnée
    @Test
    public void shouldCreateDiver () throws SQLException{
        initializeDatabaseConnection();
        DiverDAO diverDAO = new DiverDAO(connection);
        Avatar newAvatar = new Avatar("toto","red");
        diverDAO.create(newAvatar);
        diverDAO.delete(newAvatar.getId());  //suppression pour éviter de surcharger la DB
    }
    //Doit créer un Diver puis le mettre à jour à l'aide d'un nouveau Diver
    @Test
    public void shouldUpdateDiver () throws SQLException{
        initializeDatabaseConnection();
        DiverDAO diverDAO = new DiverDAO(connection);
        Avatar newAvatar = new Avatar("toto","red");
        diverDAO.create(newAvatar);  //creation d'un nouveau diver
        Avatar updatedAvatar = new Avatar("titi","blue");
        diverDAO.update(updatedAvatar, newAvatar.getId());
        diverDAO.delete(newAvatar.getId()); //suppression pour éviter de surcharger la DB
    }
    // Doit créer un nouveau Diver et le supprimer juste après
    @Test
    public void shouldDeleteDiver () throws SQLException {
        initializeDatabaseConnection();
        DiverDAO diverDAO = new DiverDAO(connection);
        Avatar newAvatar = new Avatar("toto", "red");
        diverDAO.create(newAvatar);  //creation d'un nouveau diver
        diverDAO.delete(newAvatar.getId());
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
        System.out.print(diverDAO.findAllPseudo());
    }

}
