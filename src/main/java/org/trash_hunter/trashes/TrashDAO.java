package org.trash_hunter.trashes;

import org.trash_hunter.util.DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrashDAO extends DataAccessObject<TrashDB> {
    //Requêtes pour la table Diver
    private static final String INSERT = "INSERT INTO Trashes (id,visible,name,x,y) VALUES (?,?, ?, ?, ?)";
    private static final String GET_ONE = "SELECT id,visible,name,x,y FROM Trashes WHERE id = ?";
    private static final String UPDATE = "UPDATE Trashes SET visible=?,x=?,y=?WHERE id = ?";
    private static final String DELETE = "DELETE FROM Trashes WHERE id = ?";
    private static final String GET_ALL = "SELECT id,visible,name,x,y FROM Trashes";
    private static final String GET_ALL_NAMES = "SELECT name FROM Trashes";
    private static final String TRUNCATE_TABLE = "TRUNCATE TABLE Trashes";
    public TrashDAO(Connection connection) {
        super(connection);
    }

    @Override
    public TrashDB findById(long id) {
        TrashDB trashDB = null;
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(GET_ONE)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                trashDB = new TrashDB();
                trashDB.setId(resultSet.getLong("id"));
                trashDB.setVisible(resultSet.getInt("visible"));
                trashDB.setName(resultSet.getString("name"));
                trashDB.setX(resultSet.getFloat("x"));
                trashDB.setY(resultSet.getFloat("y"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return trashDB;
    }

    @Override
    public List<TrashDB> findAll() {
        List<TrashDB> trashset = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(GET_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TrashDB trashDB = new TrashDB();
                trashDB.setId(resultSet.getInt("id"));
                trashDB.setVisible(resultSet.getInt("visible"));
                trashDB.setName(resultSet.getString("name"));
                trashDB.setX(resultSet.getFloat("x"));
                trashDB.setY(resultSet.getFloat("y"));
                trashset.add(trashDB);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return trashset;
    }
    public List<String> findAllNames (){
        List<String> names = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(GET_ALL_NAMES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TrashDB trashDB = new TrashDB();
                trashDB.setName(resultSet.getString("name"));
                names.add(trashDB.getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return names;
    }
    @Override
    public void update(TrashDB trashDBUpdated,long id) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE)) {
            preparedStatement.setInt(1,trashDBUpdated.getVisible());
            preparedStatement.setDouble(2, trashDBUpdated.getX());
            preparedStatement.setDouble(3, trashDBUpdated.getY());
            preparedStatement.setLong(4,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(TrashDB newtrashDB) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1,newtrashDB.getId());
            preparedStatement.setInt(2,newtrashDB.getVisible());
            preparedStatement.setString(3, newtrashDB.getName());
            preparedStatement.setDouble(4, newtrashDB.getX());
            preparedStatement.setDouble(5, newtrashDB.getY());

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
    public void clear() {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(TRUNCATE_TABLE)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
