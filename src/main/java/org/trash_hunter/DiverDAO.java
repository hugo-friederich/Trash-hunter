package org.trash_hunter;

import org.trash_hunter.util.DataAccessObject;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DiverDAO extends DataAccessObject<Diver> {

    private static final String INSERT= "INSERT into Diver (x,y,pseudo,score,score_max,date,game_time,color) VALUES(?,?,?,?,?,?,?,?)";

    private static final String GET_ONE="SELECT id,x,y,pseudo,score,score_max,creation_date,game_time,color FROM Diver" +
            " WHERE customer_id=?";

    private static final String UPDATE = "UPDATE customer SET first_name = ?, last_name=?, " +
            "email = ?, phone = ?, address = ?, city = ?, state = ?, zipcode = ? WHERE customer_id = ?";

    private static final String DELETE="DELETE FROM customer WHERE customer_id=?";

    public DiverDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Diver findById(long id) {
        Diver diver=new Diver();
        try(PreparedStatement preparedStatement=this.connection.prepareStatement(GET_ONE);){

            preparedStatement.setLong(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
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


        }catch (SQLException e){
            e.printStackTrace();
            throw  new RuntimeException(e);

        }
        return diver;
    }

    @Override
    public List<Diver> findAll() {
        return null;
        //a remplir
    }

    @Override
    public Diver update(Diver dto) {
        return null;
    }

    @Override
    public Diver create(Diver dto) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
