package org.trash_hunter.trashes;

import org.trash_hunter.util.DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrashDAO extends DataAccessObject<Trash> {
    //Requêtes pour la table Diver
    private static final String INSERT = "INSERT INTO Trashes (name,x,y,nbPoints) VALUES (?, ?, ?, ?)";
    private static final String GET_ONE = "SELECT name,x,y,nbPoints FROM Trashes WHERE id = ?";
    private static final String UPDATE = "UPDATE Trashes SET x=?,y=? WHERE id = ?";
    private static final String DELETE = "DELETE FROM Trashes WHERE id = ?";
    private static final String GET_ALL = "SELECT name,x,y,nbPoints FROM Trashes";
    private static final String TRUNCATE_TABLE = "TRUNCATE TABLE `2024-2025_s1_vs1_tp2_Trash_Hunter`.`Trashes`";
    public TrashDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Trash findById(long id) {
        Trash trash = null;
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(GET_ONE)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                trash = new Trash();
                trash.setName(resultSet.getString("name"));
                trash.setX(resultSet.getFloat("x"));
                trash.setY(resultSet.getFloat("y"));
                trash.setNbPoints(resultSet.getInt("nbPoints"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return trash;
    }

    @Override
    public List<Trash> findAll() {
        List<Trash> trashset = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(GET_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Trash trash = new Trash();
                trash.setId(resultSet.getInt("id"));
                trash.setName(resultSet.getString("name"));
                trash.setX(resultSet.getFloat("x"));
                trash.setY(resultSet.getFloat("y"));
                trash.setNbPoints(resultSet.getInt("nbPoints"));

                trashset.add(trash);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return trashset;
    }

    @Override
    public List<String> findAllPseudo() {
        return(null);
    }

    @Override
    public void update(Trash trashUpdated,long id) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE)) {
            preparedStatement.setDouble(1, trashUpdated.getX());
            preparedStatement.setDouble(2, trashUpdated.getY());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(Trash newtrash) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, newtrash.getName());
            preparedStatement.setDouble(2, newtrash.getX());
            preparedStatement.setDouble(3, newtrash.getY());
            preparedStatement.setInt(4, newtrash.getNbPoints());

            // Exécute la mise à jour et obtient les clés générées
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating diver failed, no rows affected.");
            }

            // Récupère les clés générées
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newtrash.setId(generatedKeys.getInt(1)); // Assigne l'ID généré au nouveau Diver
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
                throw new SQLException("Deleting trash failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addToBestScores(Trash newTrash) {
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
