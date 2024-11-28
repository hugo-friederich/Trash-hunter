package org.trash_hunter.util;

import java.sql.Connection;
import java.util.List;

public abstract class DataAccessObject<T extends DataTransferObject> {
    protected final Connection connection;
    protected final static  String LAST_VAL="SELECT last_value FROM";
    protected final static String CUSTOMER_SEQUENCE="hp_customer_seq";
    public DataAccessObject(Connection connection){
        super();
        this.connection=connection;
    }
    public abstract  T findById(long id);
    public abstract List<T> findAll();
    public abstract void update(T dto,long id);
    public abstract void create(T dto);
    public abstract void delete(long id);
    public abstract void clear();

}