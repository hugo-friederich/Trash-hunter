package org.trash_hunter.mobs;

import org.trash_hunter.user.Avatar;
import org.trash_hunter.util.DataTransferObject;
import org.trash_hunter.util.Direction;

public class MobDB implements DataTransferObject {
    protected int id;                          //identifiant unique au déchet
    private String name;

    protected double x,y;                       //coordonnées
    protected Direction dir;

    // Constructor
    public MobDB(){
        this.id=0;
    }

    public Shark convertMobDBtoShark() {
        Shark shark = new Shark(0,0,Direction.LEFT);
        shark.setId(this.id);
        shark.setY(this.y);
        shark.setX(this.x);
        shark.setName(this.name);
        shark.setDirection(this.dir);
        return (shark);
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

    public void setId(int id) {
        this.id = id;
    }

    public String toString (){
        return (this.name +", position = ("+x+","+y+")");
    }

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    @Override
    public int getId() {
        return this.id;
    }
}
