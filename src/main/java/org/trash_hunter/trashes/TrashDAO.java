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
    private static final String INSERT = "INSERT INTO Trashes (id,visible,name,x,y,nbPoints,appearanceRangeYInf,appearanceRangeYSup) VALUES (?,?, ?, ?, ?,?,?,?)";
    private static final String GET_ONE = "SELECT id,visible,name,x,y,nbPoints,appearanceRangeYInf,appearanceRangeYSup FROM Trashes WHERE id = ?";
    private static final String UPDATE = "UPDATE Trashes SET visible=?,x=?,y=?WHERE id = ?";
    private static final String DELETE = "DELETE FROM Trashes WHERE id = ?";
    private static final String GET_ALL = "SELECT id,visible,name,x,y,nbPoints,appearanceRangeYinf,appearanceRangeYSup FROM Trashes";
    private static final String TRUNCATE_TABLE = "TRUNCATE TABLE Trashes";
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
                trash.setId(resultSet.getInt("id"));
                trash.setVisible(resultSet.getInt("visible"));
                trash.setName(resultSet.getString("name"));
                trash.setX(resultSet.getFloat("x"));
                trash.setY(resultSet.getFloat("y"));
                trash.setNbPoints(resultSet.getInt("nbPoints"));
                trash.setAppearanceRangeYInf(resultSet.getInt("appearanceRangeYInf"));
                trash.setAppearanceRangeYSup(resultSet.getInt("appearanceRangeYSup"));
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
                trash.setVisible(resultSet.getInt("visible"));
                trash.setName(resultSet.getString("name"));
                trash.setX(resultSet.getFloat("x"));
                trash.setY(resultSet.getFloat("y"));
                trash.setNbPoints(resultSet.getInt("nbPoints"));
                trash.setAppearanceRangeYInf(resultSet.getInt("appearanceRangeYInf"));
                trash.setAppearanceRangeYSup(resultSet.getInt("appearanceRangeYSup"));

                trashset.add(trash);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return trashset;
    }

    @Override
    public List<String> findAllPseudoFromBestScore() {
        return(null);
    }

    @Override
    public void update(Trash trashUpdated,long id) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE)) {
            preparedStatement.setInt(1,trashUpdated.isVisible());
            preparedStatement.setDouble(2, trashUpdated.getX());
            preparedStatement.setDouble(3, trashUpdated.getY());
            preparedStatement.setLong(4,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(Trash newtrash) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1,newtrash.getId());
            preparedStatement.setInt(2,newtrash.isVisible());
            preparedStatement.setString(3, newtrash.getName());
            preparedStatement.setDouble(4, newtrash.getX());
            preparedStatement.setDouble(5, newtrash.getY());
            preparedStatement.setInt(6, newtrash.getNbPoints());
            preparedStatement.setInt(7, newtrash.getAppearanceRangeYInf());
            preparedStatement.setInt(8, newtrash.getAppearanceRangeYSup());

            // Exécute la mise à jour et obtient les clés générées
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating trash failed, no rows affected.");
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
