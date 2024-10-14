package org.trash_hunter;

import org.trash_hunter.trashes.Bottle;
import org.trash_hunter.trashes.Trash;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game {
    private BufferedImage backgroundImage;
    private Boolean isVisible;
    private int score;
    private Diver myDiver;
    private Trash[] trashset = new Trash[10];
    private Random random = new Random();

    public Game(){
        try{
            this.backgroundImage= ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("fond_marin_1440x780.png")));
        }catch (IOException ex){
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE,null,ex);
        }
        this.score=0;
        this.myDiver=new Diver();
        initTrashes();
    }
    public void rending(Graphics2D contexte){
        contexte.drawImage(this.backgroundImage,0,0,null);
        contexte.drawString("Score : "+ score,10,20);
        this.myDiver.rendering(contexte);
        for (Trash trash:this.trashset) {
            trash.rendering(contexte);
        }
    }
    public void update(){
        this.myDiver.update();
        checkCollisionWithPanel();
        if(checkCollisionDiverTrash()){
            this.score+=1;
        }
    }
    public Diver getDiver(){return this.myDiver;}
    public boolean isFinished() {return this.score>1000;}
    public void checkCollisionWithPanel(){
        if (myDiver.getX() > backgroundImage.getWidth() - myDiver.getWidth()) {myDiver.setX(0);}  // collision avec le bord droit de la scene
        if (myDiver.getX() < 0) {myDiver.setX(backgroundImage.getWidth()-myDiver.getWidth());}  // collision avec le bord gauche de la scene
        if (myDiver.getY() > backgroundImage.getHeight() - myDiver.getHeight()) {myDiver.setY(backgroundImage.getHeight()-myDiver.getWidth());}  // collision avec le bord bas de la scene
        if (myDiver.getY() < 0) {myDiver.setY(0);}  // collision avec le bord haut de la scene
    }

    //à modifier pour avoir des trash et non que bouteilles
    public boolean checkCollisionDiverTrash() {
        Boolean result = false;
        for (Trash bottle:trashset) {
            if (bottle.getX() < myDiver.x + myDiver.getWidth() &&
                    bottle.getX() + bottle.getWidth() > myDiver.x &&
                    bottle.getY() < myDiver.y + myDiver.getHeight() &&
                    bottle.getY() + bottle.getHeight() > myDiver.y)
            {result=true;}
        }
        return (result);
    }


    private void initTrashes() {
        for (int i = 0; i < this.trashset.length; i++) {
            this.trashset[i] = new org.trash_hunter.trashes.Bottle(this.random.nextInt(1200), this.random.nextInt(800));
        }
    }
/*
    public void addNewTrash() {
        for (org.trash_hunter.trashes.Trash trash : trashes) {
            if (!trash.isVisible()) {
                trash.resetPosition(1200, 800);
                break;
            }
        }
    }
    public boolean checkCollisionWithTrash() {
        boolean result=false;
        for (org.trash_hunter.trashes.Trash trash : trashes) {
            if ((trash.getX() >= myDiver.getX() + myDiver.getWidth())
                    || (trash.getX() + trash.getWidth() <= myDiver.getX())
                    || (trash.getY() >= myDiver.getY() + myDiver.getHeight())
                    || (trash.getY() + trash.getHeight() <= myDiver.getY()))
            {result = false;}
            else {result=true;}
        }
        return(result);
    }

 */

}
