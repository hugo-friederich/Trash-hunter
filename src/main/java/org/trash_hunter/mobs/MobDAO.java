package org.trash_hunter.mobs;

import org.trash_hunter.trashes.TrashDB;
import org.trash_hunter.util.DataAccessObject;
import org.trash_hunter.util.Direction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MobDAO extends DataAccessObject<MobDB> {
    //Requêtes pour la table Diver
    private static final String INSERT = "INSERT INTO Mob (id,name,x,y,dir) VALUES (?,?,?, ?,?)";
    private static final String GET_ONE = "SELECT name,x,y,dir FROM Mob WHERE id = ?";
    private static final String UPDATE = "UPDATE Mob SET x=?,y=?,dir=? WHERE id = ?";
    private static final String DELETE = "DELETE FROM Mob WHERE id = ?";
    private static final String GET_ALL = "SELECT id,name,x,y,dir FROM Mob";
    private static final String GET_ALL_NAMES = "SELECT name FROM Mob";
    private static final String TRUNCATE_TABLE = "TRUNCATE TABLE Mob";
    public MobDAO(Connection connection) {
        super(connection);
    }

    @Override
    public MobDB findById(int id) {
        org.trash_hunter.mobs.MobDB mobDB = null;
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(GET_ONE)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                mobDB = new org.trash_hunter.mobs.MobDB();
                mobDB.setId(resultSet.getInt("id"));
                mobDB.setName(resultSet.getString("name"));
                mobDB.setX(resultSet.getFloat("x"));
                mobDB.setY(resultSet.getFloat("y"));
                mobDB.setDir(Direction.valueOf(resultSet.getString("dir")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return mobDB;
    }

    @Override
    public List<MobDB> findAll() {
        List<MobDB> mobList = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(GET_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                MobDB mobDB = new MobDB();
                mobDB.setId(resultSet.getInt("id"));
                mobDB.setName(resultSet.getString("name"));
                mobDB.setX(resultSet.getFloat("x"));
                mobDB.setY(resultSet.getFloat("y"));
                mobDB.setDir(Direction.valueOf(resultSet.getString("dir")));
                mobList.add(mobDB);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return mobList;
    }
    public List<String> findAllNames (){
        List<String> names = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(GET_ALL_NAMES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                MobDB mobDB = new MobDB();
                mobDB.setName(resultSet.getString("name"));
                names.add(mobDB.getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return names;
    }
    @Override
    public void update(MobDB mobDBUpdated, int id) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE)) {
            preparedStatement.setDouble(1, mobDBUpdated.getX());
            preparedStatement.setDouble(2, mobDBUpdated.getY());
            preparedStatement.setString(3, mobDBUpdated.getDir().name());
            preparedStatement.setInt(4,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(MobDB newmobDB) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1,newmobDB.getId());
            preparedStatement.setString(2, newmobDB.getName());
            preparedStatement.setDouble(3, newmobDB.getX());
            preparedStatement.setDouble(4, newmobDB.getY());
            preparedStatement.setString(5, newmobDB.getDir().name());

            // Exécute la mise à jour et obtient les clés générées
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating mob failed, no rows affected.");
            }

            // Récupère les clés générées
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newmobDB.setId(generatedKeys.getInt(1)); // Assigne l'ID généré au nouveau Diver
                } else {
                    throw new SQLException("Creating mob failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting mob failed, no rows affected.");
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
