package org.trash_hunter.trashes;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Trash {
    protected double x,y;
    protected int width, height;
    protected int nbPoints;
    protected BufferedImage sprite;
    protected boolean isVisible = true;
    protected long creationTime;
    public Trash(double x,double y){
        this.x=x;
        this.y=y;
    }
    public void rendering (Graphics2D contexte){
        if(this.isVisible){
            contexte.drawImage(this.sprite,(int)x,(int)y,null);
        }
    }
    public void resetPosition(int maxX, int maxY) {
        this.x = (int) (Math.random() * (maxX - this.sprite.getWidth()));
        this.y = (int) (Math.random() * (maxY - this.sprite.getHeight()));
        creationTime = System.currentTimeMillis();
        isVisible = true;
    }
    public void setVisible(boolean visible) {
        isVisible = visible;
    }
    public boolean isExpired() {
        return System.currentTimeMillis() - creationTime > 10000;
    }
    public double getX(){return (this.x);}
    public double getY(){return (this.y);}
    public int getWidth(){return this.width;}
    public int getHeight(){return this.height;}
    public int getNbPoints(){
        return (this.nbPoints);
    }
    public boolean isVisible() {
        return isVisible;
    }
}
