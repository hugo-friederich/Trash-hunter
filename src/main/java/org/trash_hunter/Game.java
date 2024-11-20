package org.trash_hunter;

import org.trash_hunter.trashes.*;
import org.trash_hunter.util.DatabaseConnection;
import org.trash_hunter.windows.Start_window;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Game {
    private BufferedImage backgroundImage;
    final private Diver myDiver;
    private DiverDAO diverDAO;
    private List <Diver> divers;
    private final Trash[] trashset;
    private final ArrayList<Rectangle> imageBounds = new ArrayList<>();
    private final Random randomNbr;
    public Game (String pseudo,String color) throws SQLException {
        try{
            this.backgroundImage= ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("fond_marin_1440x780.png")));
        }catch (IOException ex){
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE,null,ex);
        }
        this.myDiver = new Diver(pseudo,color);
        this.diverDAO = new DiverDAO(DatabaseConnection.getConnection());
        this.diverDAO.create(myDiver);
        this.trashset=new Trash[30];
        this.randomNbr=new Random();

        initTrashes();

    }
    public Game() throws SQLException {
        this("Bob","Blue");
    }
    public void rending(Graphics2D contexte){
        contexte.drawImage(this.backgroundImage,0,0,null);
        contexte.drawString("Score : "+ this.myDiver.getScore(),10,20);
        for (Diver otherDivers : divers){
            otherDivers.rendering(contexte);
        }
        for (Trash trash : this.trashset) {
            trash.rendering(contexte);
        }
    }
    public void update(){
        this.myDiver.update();
        this.diverDAO.update(this.myDiver,this.myDiver.getId());
        this.divers= diverDAO.findAll();
        checkCollisionWithPanel();
        CollisionResult collisionResult = checkSimpleCollisionDiverTrash();
        if(collisionResult.getCollision()){
            this.myDiver.setScore(this.myDiver.getScore()+trashset[collisionResult.getIndex()].getNbPoints());
            this.myDiver.updateScoreHistory();
        }
        addNewTrash();
    }
    public boolean isFinished() {return false;} //le jeu n'a pas de fin

    //Gestion des ollisions
    public void checkCollisionWithPanel(){
        if (myDiver.getX() > backgroundImage.getWidth() - myDiver.getWidth()) {myDiver.setX(0);}  // collision avec le bord droit de la scene
        if (myDiver.getX() < 0) {myDiver.setX((float)backgroundImage.getWidth()-(float)myDiver.getWidth());}  // collision avec le bord gauche de la scene
        if (myDiver.getY() > backgroundImage.getHeight() - myDiver.getHeight()) {myDiver.setY((float)backgroundImage.getHeight()-(float)myDiver.getWidth());}  // collision avec le bord bas de la scene
        if (myDiver.getY() < 0) {myDiver.setY(0);}  // collision avec le bord haut de la scene
    }

    /** Vérifie les collisions entre le plongeur et les déchets de la manière la plus simple.
     * Ainsi, pour chaque déchet dans l'ensemble, elle teste s'il y a une collision.
     * Si une collision est détectée, le déchet devient invisible et la méthode
     * renvoie un résultat de collision avec un indicateur de succès et l'index du déchet.
     * Si aucune collision n'est détectée, elle renvoie un résultat de collision avec
     * un indicateur d'échec et -1 comme index.
     * @return CollisionResult
     */
    public CollisionResult checkSimpleCollisionDiverTrash() {
        for (int i = 0; i < trashset.length; i++) {
            Trash trash = trashset[i];
            if (isColliding(trash, myDiver)) {
                trash.setVisible(false);
                return new CollisionResult(true, i);
            }
        }
        return new CollisionResult(false, -1);
    }

    private boolean isColliding(Trash bottle, Diver diver) {
        return bottle.getX() < diver.getX() + diver.getWidth() &&
                bottle.getX() + bottle.getWidth() > diver.getX() &&
                bottle.getY() < diver.getY() + diver.getHeight() &&
                bottle.getY() + bottle.getHeight() > diver.getY();
    }

    /**
     * Renvoie true si il y a collision entre deux déchets
     * @param trash1 premier déchet
     * @param trash2 second déchet
     * @return true if collision
     */
    public static boolean checkCollisionBetweenTrashes (Trash trash1,Trash trash2) {
        return(trash2.getX() <= trash1.getX() + trash1.getWidth() +10 &&
                trash1.getX() <= trash2.getX() + trash2.getWidth() +10 &&
                trash1.getY() <= trash2.getY() + trash2.getHeight() +10 &&
                trash2.getY() <= trash1.getY() + trash1.getHeight() +10);
    }

    /**
     * Initialise l'ensemble des déchets avec une certaine proportion et une sélection aléatoire de
     * l'objet en fonction de sa taille
     * Nb total de déchets : 30 (15 petits,10 moyens, 5 gros)
     */

    //Gestion des déchets
    public void initTrashes() {
        for (int i = 0; i < this.trashset.length; i++) {
            int randomNumber = randomNbr.nextInt(1,3);     //choisis un nombre entre 1 et 2 aléatoirement
            if (i <= 15) {
                if (randomNumber == 1) {
                    Bottle bottle = new Bottle();
                    bottle.resetPosition();
                    trashset[i] = bottle;
                } else if (randomNumber == 2) {
                    Can can = new Can();
                    can.resetPosition();
                    trashset[i] = can;
                }
            } else if (i <= 25) {
                if (randomNumber == 1) {
                    PlasticBag plasticBag = new PlasticBag();
                    plasticBag.resetPosition();
                    trashset[i] = plasticBag;
                } else if (randomNumber == 2) {
                    Tire tire = new Tire();
                    tire.resetPosition();
                    trashset[i] = tire;
                }
            } else {
                if (randomNumber == 1) {
                    OilContainer oilContainer = new OilContainer();
                    oilContainer.resetPosition();
                    trashset[i] = oilContainer;
                } else if (randomNumber == 2) {
                    Boat boat = new Boat();
                    boat.resetPosition();
                    trashset[i] = boat;
                }
            }
        }
    }
    public void addNewTrash() {
        for (Trash trash : this.trashset) {
            if (!trash.isVisible()||trash.isExpired()) {
                trash.resetPosition();
                break;
            }
        }

    }
    //Getters and Setters
    public Trash[] getTrashset(){
        return this.trashset;
    }
    public Diver getDiver(){return this.myDiver;}

    public DiverDAO getDiverDAO() {
        return diverDAO;
    }

}
