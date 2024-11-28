package org.trash_hunter;

import org.trash_hunter.trashes.*;
import org.trash_hunter.user.Avatar;
import org.trash_hunter.user.Diver;
import org.trash_hunter.user.DiverDAO;
import org.trash_hunter.util.DatabaseConnection;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import javax.imageio.ImageIO;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game {
    private BufferedImage backgroundImage;
    private final Avatar myAvatar;
    private Diver myDiver;
    private DiverDAO diverDAO;
    private TrashDAO trashDAO;
    private List<Diver> allDivers;
    private List<Trash> localTrashset;
    private final Random randomNbr;
    private static final int NB_TRASHES = 30;

    public Game(String pseudo, String color) throws SQLException {
        this.randomNbr = new Random();

        // Chargement du fond
        loadBackgroundImage();

        // Création du joueur et communication avec la BDD
        this.myDiver = new Diver(pseudo, color);
        this.diverDAO = new DiverDAO(DatabaseConnection.getConnection());
        this.diverDAO.create(myDiver);
        this.myAvatar = myDiver.convertDiverToAvatar();

        // Initialisation des déchets
        this.trashDAO = new TrashDAO(DatabaseConnection.getConnection());
        this.trashDAO.clear();
        initLocalTrashes();
        initDBTrashes();
    }

    public Game() throws SQLException {
        this("Bob", "Blue");
    }

    private void loadBackgroundImage() {
        try {
            this.backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("fond_marin_1440x780.png")));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void rendering(Graphics2D contexte) {
        contexte.drawImage(this.backgroundImage, 0, 0, null);
        contexte.drawString("Score : " + this.myAvatar.getScore(), 10, 20);
        for (Diver otherDivers : allDivers) {
            Avatar avatar = otherDivers.convertDiverToAvatar();
            avatar.rendering(contexte);
        }
        for (Trash trash : this.localTrashset) {
            trash.rendering(contexte);
        }
    }

    public void update() throws SQLException {
        // Mise à jour du plongeur
        this.myAvatar.update();
        this.myDiver = myAvatar.convertAvatarToDiver();
        this.allDivers = diverDAO.findAll();
        this.diverDAO.update(this.myDiver, this.myDiver.getId());

        // Mise à jour des déchets après collision
        updateAfterCollisionDiverTrash();
        checkCollisionWithPanel();
    }

    public boolean isFinished() {
        return false; // Le jeu n'a pas de fin
    }

    public void checkCollisionWithPanel() {
        if (myAvatar.getX() > backgroundImage.getWidth() - myAvatar.getWidth()) {
            myAvatar.setX(0); // Collision avec le bord droit
        }
        if (myAvatar.getX() < 0) {
            myAvatar.setX(backgroundImage.getWidth() - myAvatar.getWidth()); // Collision avec le bord gauche
        }
        if (myAvatar.getY() > backgroundImage.getHeight() - myAvatar.getHeight()) {
            myAvatar.setY(backgroundImage.getHeight() - myAvatar.getHeight()); // Collision avec le bord bas
        }
        if (myAvatar.getY() < 0) {
            myAvatar.setY(0); // Collision avec le bord haut
        }
    }

    public void updateAfterCollisionDiverTrash() {
        for (int id = 0; id < localTrashset.size(); id++) {
            Trash trash = localTrashset.get(id);
            if (isColliding(trash, myAvatar)) {
                myAvatar.setScore(myAvatar.getScore() + trash.getNbPoints());
                myAvatar.updateScoreHistory();
                trash.setVisible(0);
                trash.updatePosition(); // Mise à jour de la position directement
                trashDAO.update(trash, id);
            }
        }
    }

    private boolean isColliding(Trash trash, Avatar avatar) {
        return trash.getX() < avatar.getX() + avatar.getWidth() &&
               trash.getX() + trash.getWidth() > avatar.getX() &&
               trash.getY() < avatar.getY() + avatar.getHeight() &&
               trash.getY() + trash.getHeight() > avatar.getY();
    }

    public static boolean checkCollisionBetweenTrashes(Trash trash1, Trash trash2) {
        return (trash2.getX() <= trash1.getX() + trash1.getWidth() + 10 &&
                trash1.getX() <= trash2.getX() + trash2.getWidth() + 10 &&
                trash1.getY() <= trash2.getY() + trash2.getHeight() + 10 &&
                trash2.getY() <= trash1.getY() + trash1.getHeight() + 10);
    }

    public void initLocalTrashes() throws SQLException {
        this.localTrashset = new ArrayList<>();
        for (int i = 0; i < NB_TRASHES; i++) {
            Trash trash = createRandomTrash(i);
            if (trash != null) {
                trash.updatePosition();
                localTrashset.add(trash);
            }
        }
    }

    private Trash createRandomTrash(int index) {
        int randomNumber = randomNbr.nextInt(1, 3); // Choisit un nombre entre 1 et 2 aléatoirement
        if (index <= 15) {
            return (randomNumber == 1) ? new Bottle(index) : new Can(index);
        } else if (index <= 25) {
            return (randomNumber == 1) ? new PlasticBag(index) : new Tire(index);
        } else {
            return (randomNumber == 1) ? new OilContainer(index) : new Boat(index);
        }
    }

    public void initDBTrashes() {
        for (Trash trash : localTrashset) {
            trashDAO.create(trash);
        }
    }

    // Getters and Setters
    public Avatar getMyAvatar() {
        return this.myAvatar;
    }

    public DiverDAO getDiverDAO() {
        return diverDAO;
    }

    public List<Diver> getAllDivers() {
        return allDivers;
    }

    public List<Trash> getLocalTrashset() {
        return localTrashset;
    }

    public void setLocalTrashset(List<Trash> localTrashset) {
        this.localTrashset = localTrashset;
    }

    public Diver getMyDiver() {
        return myDiver;
    }

    public void setMyDiver(Diver myDiver) {
        this.myDiver = myDiver;
    }
}




    /**
     * Initialise l'ensemble des déchets avec une certaine proportion et une sélection aléatoire de
     * l'objet en fonction de sa taille
     * Nb total de déchets : 30 (15 petits,10 moyens, 5 gros)
     */
/*
    //Gestion des déchets
    public void initLocalTrashes() throws SQLException {
        this.localTrashset = new ArrayList<>();
        for (int i = 0; i < this.nbTrashes; i++) {
            int randomNumber = randomNbr.nextInt(1,3);     //choisis un nombre entre 1 et 2 aléatoirement
            if (i <= 15) {
                if (randomNumber == 1) {
                    Bottle bottle = new Bottle(i);
                    bottle.updatePosition();
                    localTrashset.add(bottle);
                } else if (randomNumber == 2) {
                    Can can = new Can(i);
                    can.updatePosition();
                    localTrashset.add(can);
                }
            } else if (i <= 25) {
                if (randomNumber == 1) {
                    PlasticBag plasticBag = new PlasticBag(i);
                    plasticBag.updatePosition();
                    localTrashset.add(plasticBag);
                } else if (randomNumber == 2) {
                    Tire tire = new Tire(i);
                    tire.updatePosition();
                    localTrashset.add(tire);
                }
            } else {
                if (randomNumber == 1) {
                    OilContainer oilContainer = new OilContainer(i);
                    oilContainer.updatePosition();
                    localTrashset.add(oilContainer);
                } else if (randomNumber == 2) {
                    Boat boat = new Boat(i);
                    boat.updatePosition();
                    localTrashset.add(boat);
                }
            }
        }
    }

 */

    /**
     * Permet d'initialiser pour la première fois les déchets dans la base de
     * donnée.
     */
    /*
    public void initDBTrashes (){
        for (Trash trash : localTrashset){
            trashDAO.create(trash);
        }
    }
    //Getters and Setters
    public Avatar getMyAvatar(){return this.myAvatar;}

    public DiverDAO getDiverDAO() {
        return diverDAO;
    }

    public List<Diver> getAllDivers() {
        return allDivers;
    }

    public List<Trash> getLocalTrashset() {
        return localTrashset;
    }

    public void setLocalTrashset(List<Trash> localTrashset) {
        this.localTrashset = localTrashset;
    }

    public Diver getMyDiver() {
        return myDiver;
    }

    public void setMyDiver(Diver myDiver) {
        this.myDiver = myDiver;
    }
}

*/

