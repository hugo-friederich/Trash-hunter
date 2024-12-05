package org.trash_hunter.user;

import org.trash_hunter.util.DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.trash_hunter.util.OutilsJDBC.recupererResultSetEnString;

public class DiverDAO extends DataAccessObject<DiverDB> {

    //Requêtes pour la table Diver
    private static final String INSERT = "INSERT INTO Diver (x, y, pseudo, score, score_max, color) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String GET_ONE = "SELECT id, x, y, pseudo, score, score_max, creation_date, game_time, color FROM Diver WHERE id = ?";
    private static final String UPDATE = "UPDATE Diver SET x = ?, y = ?, pseudo = ?, score = ?, score_max = ?, color = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM Diver WHERE id = ?";
    private static final String GET_ALL = "SELECT id, x, y, pseudo, score, score_max, creation_date, game_time, color FROM Diver";
    private static final String GET_ALL_PSEUDO_FROM_DIVER= "SELECT pseudo FROM Diver";
    private static final String GET_ALL_DIVERS = "SELECT pseudo,score,score_max,creation_date,color FROM Diver";

    //Requêtes pour la table Best_scores
    private static final String GET_ALL_PSEUDO_FROM_BEST_SCORE = "SELECT pseudo FROM Best_scores";
    private static final String INSERT_SCORE = "INSERT INTO Best_scores (x, y, pseudo, score, score_max, color) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String TRUNCATE_TABLE = "TRUNCATE TABLE Diver";
    public DiverDAO(Connection connection) {
        super(connection);
    }

    @Override
    public DiverDB findById(long id) {
        DiverDB diverDB = null;
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(GET_ONE)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                diverDB = new DiverDB();
                diverDB.setId(resultSet.getLong("id"));
                diverDB.setX(resultSet.getFloat("x"));
                diverDB.setY(resultSet.getFloat("y"));
                diverDB.setPseudo(resultSet.getString("pseudo"));
                diverDB.setScore(resultSet.getInt("score"));
                diverDB.setScore_max(resultSet.getInt("score_max"));
                diverDB.setCreation_date(resultSet.getDate("creation_date"));
                diverDB.setGame_time(resultSet.getTime("game_time"));
                diverDB.setColor(resultSet.getString("color"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return diverDB;
    }

    @Override
    public List<DiverDB> findAll() {
        List<DiverDB> diverDBS = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(GET_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                DiverDB diverDB = new DiverDB();
                diverDB.setId(resultSet.getLong("id"));
                diverDB.setX(resultSet.getFloat("x"));
                diverDB.setY(resultSet.getFloat("y"));
                diverDB.setPseudo(resultSet.getString("pseudo"));
                diverDB.setScore(resultSet.getInt("score"));
                diverDB.setScore_max(resultSet.getInt("score_max"));
                diverDB.setCreation_date(resultSet.getDate("creation_date"));
                diverDB.setGame_time(resultSet.getTime("game_time"));
                diverDB.setColor(resultSet.getString("color"));
                diverDBS.add(diverDB);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return diverDBS;
    }
    public List<String> findAllPseudoFromDiver(){
        List<String> divers = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(GET_ALL_PSEUDO_FROM_DIVER)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                DiverDB diverDB = new DiverDB();
                diverDB.setPseudo(resultSet.getString("pseudo"));
                divers.add(diverDB.getPseudo());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return divers;
    }

    public List<String> findAllPseudoFromBestScore() {
        List<String> divers = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(GET_ALL_PSEUDO_FROM_BEST_SCORE)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                DiverDB diverDB = new DiverDB();
                diverDB.setPseudo(resultSet.getString("pseudo"));
                divers.add(diverDB.getPseudo());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return divers;
    }

    @Override
    public void update(DiverDB updatedDiverDB, long id) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE)) {
            preparedStatement.setFloat(1, updatedDiverDB.getX());
            preparedStatement.setFloat(2, updatedDiverDB.getY());
            preparedStatement.setString(3, updatedDiverDB.getPseudo());
            preparedStatement.setInt(4, updatedDiverDB.getScore());
            preparedStatement.setInt(5, updatedDiverDB.getScore_max());
            preparedStatement.setString(6, updatedDiverDB.getColor());
            preparedStatement.setLong(7, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(DiverDB newDiverDB) {
        newDiverDB.setX(newDiverDB.getX());
        newDiverDB.setY(newDiverDB.getY());
        newDiverDB.setPseudo(newDiverDB.getPseudo());
        newDiverDB.setScore(newDiverDB.getScore());
        newDiverDB.setScore_max(newDiverDB.getScore_max());
        newDiverDB.setColor(newDiverDB.getColor());
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setFloat(1, newDiverDB.getX());
            preparedStatement.setFloat(2, newDiverDB.getY());
            preparedStatement.setString(3, newDiverDB.getPseudo());
            preparedStatement.setInt(4, newDiverDB.getScore());
            preparedStatement.setInt(5, newDiverDB.getScore_max());
            preparedStatement.setString(6, newDiverDB.getColor());

            // Exécute la mise à jour et obtient les clés générées
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating diver failed, no rows affected.");
            }

            // Récupère les clés générées
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newDiverDB.setId(generatedKeys.getLong(1)); // Assigne l'ID généré au nouveau Diver
                } else {
                    throw new SQLException("Creating diver failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(DELETE)) {
            preparedStatement.setLong(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting diver failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void addToBestScores(DiverDB diverDB) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(INSERT_SCORE, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setFloat(1, diverDB.getX());
            preparedStatement.setFloat(2, diverDB.getY());
            preparedStatement.setString(3, diverDB.getPseudo());
            preparedStatement.setInt(4, diverDB.getScore());
            preparedStatement.setInt(5, diverDB.getScore_max());
            preparedStatement.setString(6, diverDB.getColor());

            // Exécute la mise à jour et obtient les clés générées
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating diver failed, no rows affected.");
            }

            // Récupère les clés générées
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    diverDB.setId(generatedKeys.getLong(1)); // Assigne l'ID généré au nouveau Diver
                } else {
                    throw new SQLException("Add diver to best scores failed");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public void clear() {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(TRUNCATE_TABLE)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public String showDiversAsString() {
        String resultString = "";
        try {
            PreparedStatement requete = this.connection.prepareStatement(GET_ALL_DIVERS);
            ResultSet resultat = requete.executeQuery();
            resultString = recupererResultSetEnString(resultat);
            requete.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultString; // Retourne la chaîne de résultats
    }
}