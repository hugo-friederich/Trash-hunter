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
    private Diver diver;
    private DiverDAO diverDAO;
    private List<Diver> allDivers;
    private TrashDAO trashDAO;
    private List<Trash> localTrashset;
    private final Random randomNbr;
    private static final int NB_TRASHES = 30;

    public Game(String pseudo, String color) throws SQLException {
        this.randomNbr = new Random();

        // Chargement du fond
        loadBackgroundImage();

        // Création du joueur et communication avec la BDD
        this.diver = new Diver(pseudo, color);
        this.diverDAO = new DiverDAO(DatabaseConnection.getConnection());
        this.diverDAO.create(diver);
        this.myAvatar = diver.convertDiverToAvatar();

        // Initialisation des déchets
        this.trashDAO = new TrashDAO(DatabaseConnection.getConnection());
        if (diverDAO.findAll().size()==1){      //initialise les déchets pour le 1er joueur
            initTrashes();
        }
        initLocalTrashes();
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
        this.diver = myAvatar.convertAvatarToDiver();
        this.allDivers = diverDAO.findAll();
        this.diverDAO.update(this.diver, this.diver.getId());

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
                trash.updatePosition(); // Mise à jour de la position directement
                trashDAO.update(trash.convertTrashToTrashDB(), id);
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

    public void initTrashes() throws SQLException {
        this.trashDAO.clear();
        List <Trash> initialTrashset = new ArrayList<>();
        for (int i = 0; i < NB_TRASHES; i++) {
            Trash trash = createRandomTrash(i);
            if (trash != null) {
                trash.updatePosition();
                initialTrashset.add(trash);
            }
        }
        for (Trash trash : initialTrashset) {
            trashDAO.create(trash.convertTrashToTrashDB());
        }
    }
    public void initLocalTrashes(){
        List <TrashDB> interTrashset = trashDAO.findAll();
        localTrashset = new ArrayList<>();
        for (TrashDB trash : interTrashset ){
            double x = trash.getX();
            double y = trash.getY();
            switch (trash.getName()){
                case "Boat" :
                    localTrashset.add(new Boat(x,y));
                    break;
                case "Bottle":
                    localTrashset.add(new Bottle(x,y));
                    break;
                case "Can":
                    localTrashset.add(new Can(x,y));
                    break;
                case "OilContainer":
                    localTrashset.add(new OilContainer(x,y));
                    break;
                case "PlasticBag":
                    localTrashset.add(new PlasticBag(x,y));
                    break;
                case "Tire":
                    localTrashset.add(new Tire(x,y));
                    break;
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
    public Diver getDiver() {
        return diver;
    }

    public void setDiver(Diver diver) {
        this.diver = diver;
    }

    public BufferedImage getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(BufferedImage backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public void setDiverDAO(DiverDAO diverDAO) {
        this.diverDAO = diverDAO;
    }

    public void setAllDivers(List<Diver> allDivers) {
        this.allDivers = allDivers;
    }

    public TrashDAO getTrashDAO() {
        return trashDAO;
    }

    public void setTrashDAO(TrashDAO trashDAO) {
        this.trashDAO = trashDAO;
    }

    public List<Trash> getLocalTrashset() {
        return localTrashset;
    }

    public void setLocalTrashset(List<Trash> localTrashset) {
        this.localTrashset = localTrashset;
    }

    public Random getRandomNbr() {
        return randomNbr;
    }
}