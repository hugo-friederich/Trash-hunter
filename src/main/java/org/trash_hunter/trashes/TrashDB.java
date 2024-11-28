package org.trash_hunter.trashes;

import org.trash_hunter.util.DataTransferObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class TrashDB implements DataTransferObject {
    protected long id;                          //identifiant unique au déchet
    private int visible;                        //déchet visible ou non
    private String name;

    protected double x,y;                       //coordonnées
    public TrashDB(String name){
        this.name=name;
        this.x=0;
        this.y=0;
        this.visible=1;
    }
    public TrashDB(){
        this("trash");
    }
    public void setVisible(int visible) {
        this.visible = visible;
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

    public int getVisible() {
        return visible;
    }

    public String toString (){
        return (this.name +", position = ("+x+","+y+"), "+"etat = "+visible);
    }
    @Override
    public long getId() {
        return this.id;
    }
}
