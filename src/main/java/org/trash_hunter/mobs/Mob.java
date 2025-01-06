package org.trash_hunter.mobs;

import org.trash_hunter.trashes.TrashDB;
import org.trash_hunter.util.Collidable;
import org.trash_hunter.util.Direction;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public abstract class Mob implements Collidable {
    
    // ATTRIBUTS
    protected int id ;                          //identifiant unique
    protected String name;                      //nom commun

    protected double x,y;                       //coordonnées
    protected int width, height;                //largeur, hauteur
    protected int nbPoints;                     //nombre de points à retrancher
    protected int nbLife;                       //nombre de vie à retrancher
    protected BufferedImage sprite;             //image du mob
    protected int speed;                        //Vitesse de déplacement
    protected Direction direction;              //Direction de déplacement (left or right)

    public Mob(int id,double x, double y,Direction dir){
        this.x=x;
        this.y=y;
        this.direction=dir;
    }
    public void rendering (Graphics2D contexte) {
        contexte.drawImage(this.sprite, (int) x, (int) y, null);

    }
    public MobDB convertMobToMobDB(){
        MobDB mobDB = new MobDB();
        mobDB.setName(name);
        mobDB.setId(id);
        mobDB.setX(x);
        mobDB.setY(y);
        mobDB.setDir(direction);
        return(mobDB);
    }
    public static Mob convertMobDBToMob(MobDB mobDB) {
        switch (mobDB.getName()) {
            case "Shark":
                return new Shark(mobDB.getId(), mobDB.getX(), mobDB.getDir());
            default:
                return null;
        }
    }
    public abstract void update();

    // Getters and setters

    public double getX(){return (this.x);}
    public double getY(){return (this.y);}
    public int getWidth(){return this.width;}
    public int getHeight(){return this.height;}
    public BufferedImage getSprite(){
        return(sprite);
    }
    public void setDirection (Direction newDirection){
        direction=newDirection;
    }

    public int getNbPoints() {
        return nbPoints;
    }

    public void setNbPoints(int nbPoints) {
        this.nbPoints = nbPoints;
    }

    public int getNbLife() {
        return nbLife;
    }

    public void setNbLife(int nbLife) {
        this.nbLife = nbLife;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String toString (){
        return this.name;
    }
}

    

