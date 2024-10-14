package org.trash_hunter;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Diver {
    protected BufferedImage sprite;
    protected double x, y;
    protected int width, height;
    protected int speed;
    private boolean left, right, up, down;

    public Diver() {
        try {
            this.sprite = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("plongeur.png")));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement de l'image : " + e.getMessage(), e);
        }
        run();
    }
    public void update(){
        if (this.left) {x -= this.speed;}
        if (this.right) {x += this.speed;}
        if (this.down) {y += this.speed;}
        if (this.up) {y -= this.speed;}
    }
    public void rendering (Graphics2D contexte){
        contexte.drawImage(this.sprite,(int)x,(int)y,null);
    }

    public void run() {
        this.x = 170;
        this.y = 300;
        this.left = false;
        this.right = false;
        this.up = false;
        this.down = false;
        this.width = 50;
        this.height = 50;
        this.speed = 10;

    }
    public double getX(){return x;}
    public double getY(){
        return y;
    }
    public void setX(double x){this.x=x;}
    public void setY(double y){this.y=y;}

    public double getHeight(){
        return sprite.getHeight();
    }
    public double getWidth(){
        return sprite.getWidth();
    }
    public void setRight(boolean right){
        this.right=right;
    }
    public void setLeft(boolean left){
        this.left=left;
    }
    public void setUp(boolean up){
        this.up=up;
    }
    public void setDown(boolean down){
        this.down=down;
    }
    public void setSpeed(int speed){this.speed=speed;}
}
