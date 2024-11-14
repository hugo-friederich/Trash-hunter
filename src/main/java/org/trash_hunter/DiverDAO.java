package org.trash_hunter;

import org.trash_hunter.util.DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiverDAO extends DataAccessObject<Diver> {

    private static final String INSERT = "INSERT INTO Diver (x, y, pseudo, score, score_max, creation_date, game_time, color) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_ONE = "SELECT id, x, y, pseudo, score, score_max, creation_date, game_time, color FROM Diver WHERE id = ?";
    private static final String UPDATE = "UPDATE Diver SET x = ?, y = ?, pseudo = ?, score = ?, score_max = ?, creation_date = ?, game_time = ?, color = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM Diver WHERE id = ?";
    private static final String GET_ALL = "SELECT id, x, y, pseudo, score, score_max, creation_date, game_time, color FROM Diver";

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
    public Diver update(Diver dto) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE)) {
            preparedStatement.setFloat(1, dto.getX());
            preparedStatement.setFloat(2, dto.getY());
            preparedStatement.setString(3, dto.getPseudo());
            preparedStatement.setInt(4, dto.getScore());
            preparedStatement.setInt(5, dto.getScoreMax());
            preparedStatement.setDate(6, new java.sql.Date(dto.getDate().getTime()));
            preparedStatement.setTime(7, new java.sql.Time(dto.getGame_time().getTime()));
            preparedStatement.setString(8, dto.getColor());
            preparedStatement.setLong(9, dto.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return dto;
    }

    @Override
    public Diver create(Diver newDiver) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setFloat(1, newDiver.getX());
            preparedStatement.setFloat(2, newDiver.getY());
            preparedStatement.setString(3, newDiver.getPseudo());
            preparedStatement.setInt(4, newDiver.getScore());
            preparedStatement.setInt(5, newDiver.getScoreMax());
            preparedStatement.setDate(6, new java.sql.Date(newDiver.getDate().getTime()));
            preparedStatement.setTime(7, new java.sql.Time(newDiver.getGame_time().getTime()));
            preparedStatement.setString(8, newDiver.getColor());

            // Exécute la mise à jour et obtient les clés générées
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating diver failed, no rows affected.");
            }

            // Récupère les clés générées
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newDiver.setId(generatedKeys.getLong(1)); // Assigne l'ID généré à l'objet diver
                } else {
                    throw new SQLException("Creating diver failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return newDiver; // Retourne l'objet diver avec l'ID généré
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
}