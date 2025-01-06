package org.trash_hunter.mobs;

import org.trash_hunter.util.DataTransferObject;
import org.trash_hunter.util.Direction;

public class MobDB implements DataTransferObject {
    protected long id;                          //identifiant unique au déchet
    private String name;

    protected double x,y;                       //coordonnées
    protected Direction dir;

    // Constructor
    public MobDB(){
        this.id=0;
    }


    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String toString (){
        return (this.name +", position = ("+x+","+y+")");
    }
    @Override
    public long getId() {
        return this.id;
    }
}
