package org.trash_hunter.user;

import org.trash_hunter.util.DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiverDAO extends DataAccessObject<Avatar> {

    //Requêtes pour la table Diver
    private static final String INSERT = "INSERT INTO Diver (x, y, pseudo, score, score_max, color) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String GET_ONE = "SELECT id, x, y, pseudo, score, score_max, creation_date, game_time, color FROM Diver WHERE id = ?";
    private static final String UPDATE = "UPDATE Diver SET x = ?, y = ?, pseudo = ?, score = ?, score_max = ?, color = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM Diver WHERE id = ?";
    private static final String GET_ALL = "SELECT id, x, y, pseudo, score, score_max, creation_date, game_time, color FROM Diver";

    //Requêtes pour la table Best_scores
    private static final String GET_ALL_PSEUDO = "SELECT pseudo FROM Best_scores";
    private static final String INSERT_SCORE = "INSERT INTO Best_scores (x, y, pseudo, score, score_max, color) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String TRUNCATE_TABLE = "TRUNCATE TABLE Diver";
    public DiverDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Avatar findById(long id) {
        Avatar avatar = null;
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(GET_ONE)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                avatar = new Avatar();
                avatar.setId(resultSet.getLong("id"));
                avatar.setX(resultSet.getFloat("x"));
                avatar.setY(resultSet.getFloat("y"));
                avatar.setPseudo(resultSet.getString("pseudo"));
                avatar.setScore(resultSet.getInt("score"));
                avatar.setScore_max(resultSet.getInt("score_max"));
                avatar.setCreation_date(resultSet.getDate("creation_date"));
                avatar.setGame_time(resultSet.getTime("game_time"));
                avatar.setColor(resultSet.getString("color"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return avatar;
    }

    @Override
    public List<Avatar> findAll() {
        List<Avatar> avatars = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(GET_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Avatar avatar = new Avatar();
                avatar.setId(resultSet.getLong("id"));
                avatar.setX(resultSet.getFloat("x"));
                avatar.setY(resultSet.getFloat("y"));
                avatar.setPseudo(resultSet.getString("pseudo"));
                avatar.setScore(resultSet.getInt("score"));
                avatar.setScore_max(resultSet.getInt("score_max"));
                avatar.setCreation_date(resultSet.getDate("creation_date"));
                avatar.setGame_time(resultSet.getTime("game_time"));
                avatar.setColor(resultSet.getString("color"));
                avatars.add(avatar);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return avatars;
    }

    @Override
    public List<String> findAllPseudo() {
        List<String> divers = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(GET_ALL_PSEUDO)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Avatar avatar = new Avatar();
                avatar.setPseudo(resultSet.getString("pseudo"));
                divers.add(avatar.getPseudo());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return divers;
    }

    @Override
    public void update(Avatar updatedAvatar, long id) {
        Diver diverUpdated = new Diver();
        diverUpdated.setId(id);
        diverUpdated.setX(updatedAvatar.getX());
        diverUpdated.setY(updatedAvatar.getY());
        diverUpdated.setPseudo(updatedAvatar.getPseudo());
        diverUpdated.setScore(updatedAvatar.getScore());
        diverUpdated.setScore_max(updatedAvatar.getScore_max());
        diverUpdated.setColor(updatedAvatar.getColor());
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE)) {
            preparedStatement.setFloat(1, diverUpdated.getX());
            preparedStatement.setFloat(2, diverUpdated.getY());
            preparedStatement.setString(3, diverUpdated.getPseudo());
            preparedStatement.setInt(4, diverUpdated.getScore());
            preparedStatement.setInt(5, diverUpdated.getScore_max());
            preparedStatement.setString(6, diverUpdated.getColor());
            preparedStatement.setLong(7, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(Avatar newAvatar) {
        Diver newDiver = new Diver();
        newDiver.setX(newAvatar.getX());
        newDiver.setY(newAvatar.getY());
        newDiver.setPseudo(newAvatar.getPseudo());
        newDiver.setScore(newAvatar.getScore());
        newDiver.setScore_max(newAvatar.getScore_max());
        newDiver.setColor(newAvatar.getColor());
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setFloat(1, newDiver.getX());
            preparedStatement.setFloat(2, newDiver.getY());
            preparedStatement.setString(3, newDiver.getPseudo());
            preparedStatement.setInt(4, newDiver.getScore());
            preparedStatement.setInt(5, newDiver.getScore_max());
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
    public void addToBestScores(Avatar newAvatar) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(INSERT_SCORE, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setFloat(1, newAvatar.getX());
            preparedStatement.setFloat(2, newAvatar.getY());
            preparedStatement.setString(3, newAvatar.getPseudo());
            preparedStatement.setInt(4, newAvatar.getScore());
            preparedStatement.setInt(5, newAvatar.getScore_max());
            preparedStatement.setString(6, newAvatar.getColor());

            // Exécute la mise à jour et obtient les clés générées
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating diver failed, no rows affected.");
            }

            // Récupère les clés générées
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newAvatar.setId(generatedKeys.getLong(1)); // Assigne l'ID généré au nouveau Diver
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
}