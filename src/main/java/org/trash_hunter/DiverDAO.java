package org.trash_hunter;

import org.trash_hunter.util.DataAccessObject;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DiverDAO extends DataAccessObject<Diver> {

    public DiverDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Diver findById(long id) {
        return null;
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
