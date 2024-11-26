package org.trash_hunter.trashes;

import org.trash_hunter.util.Couple;
import org.trash_hunter.util.DataTransferObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Trash implements DataTransferObject {
    protected long id;                           //identifiant unique au déchet

    protected String name;                      //nom commun

    protected double x,y;                       //coordonnées
    protected int width, height;                //largeur, hauteur
    protected int nbPoints;                     //nomnbre de points rapportés
    protected BufferedImage sprite;             //image du déchet
    private int visible = 1;                    //déchet visible ou non
    protected long creationTime;                //temps passé après création
    protected int repatriationTime;             //temps de récupération en ms
    protected int appearanceRangeXInf;          //valeur inf plage d'apparition sur x
    protected int appearanceRangeXSup;          //valeur sup plage d'apparition sur x
    protected int appearanceRangeYInf;          //valeur inf plage d'apparition sur y
    protected int appearanceRangeYSup;          //valeur sup plage d'apparition sur y

    public Trash(double x,double y){
        this.x=x;
        this.y=y;
        this.appearanceRangeXInf = 0;
        this.appearanceRangeXSup = 1440-width;
    }
    // constructeur par défault
    public Trash (){
        this(0,0);
    }
    public Trash (long id){
        this(0,0);
        this.id=id;
    }
    public void rendering (Graphics2D contexte){
        if(this.visible==1){
            contexte.drawImage(this.sprite,(int)x,(int)y,null);
        }
    }

    /**
     * Permet de réinitialiser la position d'un déchet après qu'il ai été rendu invisible
     */
    public void updatePosition() {
        //objet alléatoire
        Random rdm = new Random();
        this.x = rdm.nextDouble(this.appearanceRangeXInf,this.appearanceRangeXSup+0.5);
        this.y = rdm.nextDouble(this.appearanceRangeYInf,this.appearanceRangeYSup+0.5);
        creationTime = System.currentTimeMillis();
        visible = 1;
    }
    public void setVisible(int visible) {
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

    public int isVisible() {
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

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAppearanceRangeYInf() {
        return appearanceRangeYInf;
    }

    public void setAppearanceRangeYInf(int appearanceRangeYInf) {
        this.appearanceRangeYInf = appearanceRangeYInf;
    }

    public int getAppearanceRangeYSup() {
        return appearanceRangeYSup;
    }

    public void setAppearanceRangeYSup(int appearanceRangeYSup) {
        this.appearanceRangeYSup = appearanceRangeYSup;
    }

    public int getAppearanceRangeXInf() {
        return appearanceRangeXInf;
    }

    public void setAppearanceRangeXInf(int appearanceRangeXInf) {
        this.appearanceRangeXInf = appearanceRangeXInf;
    }

    public int getAppearanceRangeXSup() {
        return appearanceRangeXSup;
    }

    public void setAppearanceRangeXSup(int appearanceRangeXSup) {
        this.appearanceRangeXSup = appearanceRangeXSup;
    }


    public String toString (){
        return (this.name);
    }
}
