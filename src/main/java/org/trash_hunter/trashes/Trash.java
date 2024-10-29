package org.trash_hunter.trashes;

import org.trash_hunter.tools.Couple;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Trash {
    protected String name;                      //nom commun

    protected double x,y;                       //coordonnées
    protected int width, height;                //largeur, hauteur
    protected int nbPoints;                     //nomnbre de points rapportés
    protected BufferedImage sprite;             //image du déchet
    private boolean visible = true;         //déchet visible ou non
    protected long creationTime;                //temps passé après création
    protected int recupTime;                         //temps de récupération en ms
    protected Couple appearanceRangeX;          //plage de valeur d'apparition des déchets sur x
    protected Couple appearanceRangeY;          //plage de valeur d'apparition des déchets sur Y
    private Random rdm;                         //objet alléatoire
    public Trash(double x,double y){
        this.x=x;
        this.y=y;
        this.appearanceRangeX = new Couple(0,1440-this.width);
    }
    // constructeur par défault
    public Trash (){
        this(0,0);
    }
    public void rendering (Graphics2D contexte){
        if(this.visible){
            contexte.drawImage(this.sprite,(int)x,(int)y,null);
        }
    }

    /**
     * Permet de réinitialiser la position d'un déchet après qu'il ai été rendu invisible
     */
    public void resetPosition() {
        this.rdm = new Random();
        this.x = rdm.nextDouble(this.appearanceRangeX.inf,this.appearanceRangeX.sup+0.5);
        this.y = rdm.nextDouble(this.appearanceRangeY.inf,this.appearanceRangeY.sup+0.5);
        creationTime = System.currentTimeMillis();
        visible = true;
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    public boolean isExpired() {
        return System.currentTimeMillis() - creationTime > 1000000;
    }

    // Getters and setters

    public double getX(){return (this.x);}
    public double getY(){return (this.y);}
    public int getWidth(){return this.width;}
    public int getHeight(){return this.height;}
    public int getNbPoints(){return (this.nbPoints);}
    public boolean isVisible() {return visible;}
    public String toString (){
        return this.name;
    }
}
