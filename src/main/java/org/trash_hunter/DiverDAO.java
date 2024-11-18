package org.trash_hunter;

import org.trash_hunter.util.DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiverDAO extends DataAccessObject<Diver> {

    //Requêtes pour la table Diver
    private static final String INSERT = "INSERT INTO Diver (x, y, pseudo, score, score_max, color) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String GET_ONE = "SELECT id, x, y, pseudo, score, score_max, creation_date, game_time, color FROM Diver WHERE id = ?";
    private static final String UPDATE = "UPDATE Diver SET x = ?, y = ?, pseudo = ?, score = ?, score_max = ?, color = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM Diver WHERE id = ?";
    private static final String GET_ALL = "SELECT id, x, y, pseudo, score, score_max, creation_date, game_time, color FROM Diver";

    //Requêtes pour la table Best_scores
    private static final String GET_ALL_PSEUDO = "SELECT pseudo FROM Best_scores";
    private static final String INSERT_SCORE = "INSERT INTO Best_scores (x, y, pseudo, score, score_max, color) VALUES (?, ?, ?, ?, ?, ?)";
    public DiverDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Diver findById(long id) {
        Diver diver = null;
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(GET_ONE)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                diver = new Diver();
                diver.setId(resultSet.getLong("id"));
                diver.setX(resultSet.getFloat("x"));
                diver.setY(resultSet.getFloat("y"));
                diver.setPseudo(resultSet.getString("pseudo"));
                diver.setScore(resultSet.getInt("score"));
                diver.setScoreMax(resultSet.getInt("score_max"));
                diver.setDate(resultSet.getDate("creation_date"));
                diver.setGame_time(resultSet.getTime("game_time"));
                diver.setColor(resultSet.getString("color"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return diver;
    }

    @Override
    public List<Diver> findAll() {
        List<Diver> divers = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(GET_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Diver diver = new Diver();
                diver.setId(resultSet.getLong("id"));
                diver.setX(resultSet.getFloat("x"));
                diver.setY(resultSet.getFloat("y"));
                diver.setPseudo(resultSet.getString("pseudo"));
                diver.setScore(resultSet.getInt("score"));
                diver.setScoreMax(resultSet.getInt("score_max"));
                diver.setDate(resultSet.getDate("creation_date"));
                diver.setGame_time(resultSet.getTime("game_time"));
                diver.setColor(resultSet.getString("color"));
                divers.add(diver);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return divers;
    }

    @Override
    public List<String> findAllPseudo() {
        List<String> divers = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(GET_ALL_PSEUDO)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Diver diver = new Diver();
                diver.setPseudo(resultSet.getString("pseudo"));
                divers.add(diver.getPseudo());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return divers;
    }

    @Override
    public void update(Diver diverUpdated,long id) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE)) {
            preparedStatement.setFloat(1, diverUpdated.getX());
            preparedStatement.setFloat(2, diverUpdated.getY());
            preparedStatement.setString(3, diverUpdated.getPseudo());
            preparedStatement.setInt(4, diverUpdated.getScore());
            preparedStatement.setInt(5, diverUpdated.getScoreMax());
            preparedStatement.setString(6, diverUpdated.getColor());
            preparedStatement.setLong(7, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(Diver newDiver) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setFloat(1, newDiver.getX());
            preparedStatement.setFloat(2, newDiver.getY());
            preparedStatement.setString(3, newDiver.getPseudo());
            preparedStatement.setInt(4, newDiver.getScore());
            preparedStatement.setInt(5, newDiver.getScoreMax());
            preparedStatement.setString(6, newDiver.getColor());

            // Exécute la mise à jour et obtient les clés générées
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating diver failed, no rows affected.");
            }

            // Récupère les clés générées
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newDiver.setId(generatedKeys.getLong(1)); // Assigne l'ID généré au nouveau Diver
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

    @Override
    public void addToBestScores(Diver newDiver) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(INSERT_SCORE, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setFloat(1, newDiver.getX());
            preparedStatement.setFloat(2, newDiver.getY());
            preparedStatement.setString(3, newDiver.getPseudo());
            preparedStatement.setInt(4, newDiver.getScore());
            preparedStatement.setInt(5, newDiver.getScoreMax());
            preparedStatement.setString(6, newDiver.getColor());

            // Exécute la mise à jour et obtient les clés générées
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating diver failed, no rows affected.");
            }

            // Récupère les clés générées
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newDiver.setId(generatedKeys.getLong(1)); // Assigne l'ID généré au nouveau Diver
                } else {
                    throw new SQLException("Add diver to best scores failed");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}