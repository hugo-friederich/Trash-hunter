import org.junit.Assert;
import org.junit.Test;
import org.trash_hunter.Diver;
import org.trash_hunter.DiverDAO;
import org.trash_hunter.util.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DiverDAOTest {
    private Connection connection;
    private void initializeDatabaseConnection() throws SQLException {
        DriverManager.registerDriver(new org.mariadb.jdbc.Driver());
        DatabaseConnectionManager databaseConnectionManager = new DatabaseConnectionManager("nemrod.ens2m.fr:3306", "2024-2025_s1_vs1_tp2_Trash_Hunter", "etudiant", "YTDTvj9TR3CDYCmP");
        connection = databaseConnectionManager.getConnection();
    }

    @Test
    public void shouldSelectDiverById () throws SQLException{
        initializeDatabaseConnection();
        DiverDAO diverSerched = new DiverDAO(connection);
        Diver diver = diverSerched.findById(1);
        System.out.print(diver.toString());
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
        diverDAO.update(updatedDiver,newDiver.getId());
        diverDAO.delete(newDiver.getId()); //suppression pour éviter de surcharger la DB
    }
    // Doit créer un nouveau Diver et le supprimer juste après
    @Test
    public void shouldDeleteDiver () throws SQLException{
        initializeDatabaseConnection();
        DiverDAO diverDAO = new DiverDAO(connection);
        Diver newDiver = new Diver("toto","red");
        diverDAO.create(newDiver);  //creation d'un nouveau diver
        diverDAO.delete(newDiver.getId());
    }
    @Test
    public void shouldFindAll () throws SQLException{
        initializeDatabaseConnection();
        DiverDAO diverDAO = new DiverDAO(connection);
        System.out.print(diverDAO.findAll().toString());
    }

}
