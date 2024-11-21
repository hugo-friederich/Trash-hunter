package org.trash_hunter.trashes;

import org.trash_hunter.util.Couple;
import org.trash_hunter.util.DataTransferObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Trash implements DataTransferObject {
    protected int id;
    protected String name;                      //nom commun

    protected double x,y;                       //coordonnées
    protected int width, height;                //largeur, hauteur
    protected int nbPoints;                     //nomnbre de points rapportés
    protected BufferedImage sprite;             //image du déchet
    private boolean visible = true;         //déchet visible ou non
    protected long creationTime;                //temps passé après création
    protected int repatriationTime;                         //temps de récupération en ms
    protected Couple appearanceRangeX;          //plage de valeur d'apparition des déchets sur x
    protected Couple appearanceRangeY;          //plage de valeur d'apparition des déchets sur Y

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
        //objet alléatoire
        Random rdm = new Random();
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    public int getNbPoints() {
        return nbPoints;
    }
    public void setNbPoints(int nbPoints) {
        this.nbPoints = nbPoints;
    }
    public BufferedImage getSprite() {
        return sprite;
    }
    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }

    public boolean isVisible() {
        return visible;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public int getRepatriationTime() {
        return repatriationTime;
    }

    public void setRepatriationTime(int repatriationTime) {
        this.repatriationTime = repatriationTime;
    }

    public Couple getAppearanceRangeX() {
        return appearanceRangeX;
    }

    public void setAppearanceRangeX(Couple appearanceRangeX) {
        this.appearanceRangeX = appearanceRangeX;
    }

    public Couple getAppearanceRangeY() {
        return appearanceRangeY;
    }

    public void setAppearanceRangeY(Couple appearanceRangeY) {
        this.appearanceRangeY = appearanceRangeY;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString (){
        return this.name;
    }
}
