package org.trash_hunter;

import org.trash_hunter.mobs.Mob;
import org.trash_hunter.mobs.MobDAO;
import org.trash_hunter.mobs.MobDB;
import org.trash_hunter.mobs.Shark;
import org.trash_hunter.trashes.*;
import org.trash_hunter.user.Avatar;
import org.trash_hunter.user.DiverDB;
import org.trash_hunter.user.DiverDAO;
import org.trash_hunter.util.Collidable;
import org.trash_hunter.util.DatabaseConnection;
import org.trash_hunter.util.Direction;
import org.trash_hunter.util.SoundManager;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.swing.*;
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
    private BufferedImage backgroundImage;                              // Image de fond du jeu
    private final Avatar myAvatar;                                      // Avatar du joueur
    private DiverDB diverDB;                                            // Plongeur actuel
    private DiverDAO diverDAO;                                          // DAO pour les plongeurs
    private List<DiverDB> allDiverDB;                                   // Liste de tous les plongeurs de la BDD
    private List<Avatar> allAvatar;                                     // Liste de tous les avatars des joueurs
    private TrashDAO trashDAO;                                          // DAO pour les déchets
    private List<Trash> localTrashset;                                  // Liste des déchets locaux
    private List<Mob> localMobs;                                        // Liste des monstres locaux
    private MobDAO mobDAO;                                              // DAO por les mobs
    private final Random randomNbr;                                     // Générateur de nombres aléatoires
    private static final int NB_TRASHES = 30;                           // Nombre de déchets à initialiser


    public Game(String pseudo, String color) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();         // Connection à la DB
        this.randomNbr = new Random();
        loadBackgroundImage();                                              // Chargement de l'image de fond

        //initialisation du plongeur
        this.diverDB = new DiverDB(pseudo, color);                          // Création du plongeur
        this.diverDAO = new DiverDAO(connection);                           // Connection à la table Diver
        this.diverDAO.create(diverDB);                                      // Enregistrement du plongeur dans la base de données
        this.myAvatar = diverDB.convertDiverToAvatar();                     // Conversion du plongeur en avatar pour l'afficher

        //initiaslisation des déchets
        this.localTrashset = new ArrayList<>();
        this.trashDAO = new TrashDAO(connection);
        if (trashDAO.findAll().isEmpty()) {
            initTrashes();                                                  // Initialise les déchets si il n'y en a pas déjà
        }
        initLocalTrashes();                                                 // Initialise les déchets locaux

        //initialise les mobs
        this.mobDAO = new MobDAO(connection);                               // Connestion à la table Mob
        //mobDAO.clear();
        if(mobDAO.findAll().isEmpty()) {
            Shark shark = new Shark(1380);                                       // Nouveau requin à droite de la fenêtre
            MobDB mobDB = shark.convertMobToMobDB();
            this.mobDAO.create(mobDB);                                          // Creation du requin dans la DB
        }
        initLocalMobs();
        playAmbientMusic();                                                 // Lance la musique d'ambiance
    }

    private void loadBackgroundImage() {
        try {
            this.backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("fond_marin_1440x780.png")));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Méthode de rendu
    public void rendering(Graphics2D contexte) {
        contexte.drawImage(this.backgroundImage, 0, 0, null);
        contexte.drawString("Score : " + this.myAvatar.getScore(), 10, 20);     // Affiche le score
        contexte.drawString("Oxygen : " + this.myAvatar.getOxygen(), 10, 40);     // Afficher oxygen
        contexte.drawString("Life : " + this.myAvatar.getLife(), 10, 60);     // Afficher nombre de vie

        // Rendu des plongeurs
        for (DiverDB diver : allDiverDB) {
            Avatar avatar = diver.convertDiverToAvatar();
            avatar.rendering(contexte);
        }

        // Rendu des déchets
        for (Trash trash : this.localTrashset) {
            trash.rendering(contexte);
        }

        // Rendu des monstres
        for (Mob mob : this.localMobs){
            mob.rendering(contexte);
        }
    }

    // Méthode de mise à jour du jeu
    public void update() throws SQLException {
        // Mise à jour de l'avatar
        this.myAvatar.update();                                 // Mise à jour de l'avatar
        this.diverDB = myAvatar.convertAvatarToDiver();           // Conversion de l'avatar en plongeur
        this.allDiverDB = diverDAO.findAll();                    // Récupération de tous les plongeurs
        this.diverDAO.update(this.diverDB, this.diverDB.getId());   // Mise à jour du plongeur dans la base de données

        // Mise à jour des déchets après collision
        updateLocalTrashes();
        updateAfterCollisionDiverTrash();
        checkCollisionDiverPanel();                              // Vérification des collisions de l'avatar avec les bords

        // Mise à jour des monstres
        updateLocalMobs();
        ubdateMobTable();
        updateAfterCollisionDiverMob();
        checkCollisionMobPanel();
    }


    // Méthode pour vérifier si le jeu est terminé
    public boolean isFinished() {
        if (this.myAvatar.getOxygen() <= 0) {              // Le joueur perd quand il n'a plus d'oxygène
            JOptionPane.showMessageDialog(null, "Vous vous êtes noyé !!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        if (this.myAvatar.getLife() <= 0) {              // Le joueur perd quand il n'a plus de vie
            JOptionPane.showMessageDialog(null, "Vous vous êtes fait dévoré!!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }

        return false;
    }

    // Mise à jour après collision entre le plongeur et les déchets
    public void updateAfterCollisionDiverTrash() {
        for (int id = 0; id < localTrashset.size(); id++) {
            Trash trash = localTrashset.get(id);
            if (isColliding(trash, myAvatar) && trash.getVisible() == 1) {
                myAvatar.setScore(myAvatar.getScore() + trash.getNbPoints());       // Mise à jour du score
                myAvatar.updateScoreHistory();                                      // Mise à jour de l'historique des scores
                makeTrashSound();

                trash.setVisible(0);
                trash.setCreationTime(System.currentTimeMillis());
                trashDAO.update(trash.convertTrashToTrashDB(), id);
                trash.updatePosition();
                trashDAO.update(trash.convertTrashToTrashDB(), id);              // Mise à jour dans la base de données
            }if(trash.getVisible()==0) {
                trash.updatePosition();
                trashDAO.update(trash.convertTrashToTrashDB(), id);              // Mise à jour dans la base de données
            }
        }
    }
    // Mise à jour après collision entre le plongeur et les monstres
    public void updateAfterCollisionDiverMob() {
        for (int id = 0; id < localMobs.size(); id++) {
            Mob mob = localMobs.get(id);
            if (isColliding(mob, myAvatar)) {
                myAvatar.setScore(myAvatar.getScore() - mob.getNbPoints());       // Mise à jour du score
                myAvatar.setLife(myAvatar.getLife()-mob.getNbLife());             // Mise à jour de la vie du joueur
                myAvatar.updateScoreHistory();                                    // Mise à jour de l'historique des scores
                makeTrashSound();
            }
        }
    }

    // Mise à jour des déchets locaux en fonction des données de la base
    public void updateLocalTrashes() {
        List<TrashDB> dataBaseTrashset = trashDAO.findAll();    // Récupération des déchets de la base de données

        // Mise à jour des coordonnées des déchets locaux
        for (TrashDB trash : dataBaseTrashset) {
            int id = (int) trash.getId();
            Trash localTrash = localTrashset.get(id);
            if (localTrash != null) {
                localTrash.setX(trash.getX()); // Mise à jour de la position X
                localTrash.setY(trash.getY()); // Mise à jour de la position Y
                localTrash.setVisible(trash.getVisible()); // Mise à jour de la visibilité
            }
        }
    }

    // Mise à jour des monstres locaux
    public void updateLocalMobs(){
        List<MobDB> dbMobs = mobDAO.findAll();    // Récupération des mobs de la DB
        //Mise à jour des coordonées des monstres
        for (MobDB mob : dbMobs){
            int id = (int) mob.getId();
            Mob localMob = this.localMobs.stream()
                    .filter(m -> m.getId() == id)
                    .findFirst()
                    .orElse(null);
            localMob.update();
            if (localMob != null) {
                localMob.setX(mob.getX()); // Mise à jour de la position X
                localMob.setY(mob.getY()); // Mise à jour de la position Y
            }
        }
    }
    private void ubdateMobTable() {
        for(int id=0;id<localMobs.size();id++) {
            Mob mob = localMobs.get(id);
            mob.update();
            this.mobDAO.update(mob.convertMobToMobDB(),id);
        }
    }

    // Vérification de la collision entre un déchet et l'avatar
    private boolean isColliding(Collidable collidable, Avatar avatar) {
        return collidable.getX() < avatar.getX() + avatar.getWidth() &&
                collidable.getX() + collidable.getWidth() > avatar.getX() &&
                collidable.getY() < avatar.getY() + avatar.getHeight() &&
                collidable.getY() + collidable.getHeight() > avatar.getY();
    }

    // Vérification des collisions avec les bords de l'écran
    public void checkCollisionDiverPanel() {
        if (myAvatar.getX() > backgroundImage.getWidth() - myAvatar.getWidth()) {
            myAvatar.setX(0);
        }
        if (myAvatar.getX() < 0) {
            myAvatar.setX(backgroundImage.getWidth() - myAvatar.getWidth());
        }
        if (myAvatar.getY() > backgroundImage.getHeight() - myAvatar.getHeight()) {
            myAvatar.setY(backgroundImage.getHeight() - myAvatar.getHeight());
        }
        if (myAvatar.getY() < 25) {
            myAvatar.setY(25);
        }
    }
    public void checkCollisionMobPanel(){
        for(Mob mob : localMobs){
            if (mob.getX()<=0){
                mob.setDirection(Direction.RIGHT);
            }
            if (mob.getX()>=1380){
                mob.setDirection(Direction.LEFT);
            }
        }
    }

    // Initialisation des déchets
    public void initTrashes() {
        this.trashDAO.clear();
        List<Trash> initialTrashset = new ArrayList<>();

        // Création de déchets aléatoires
        for (int i = 0; i < NB_TRASHES; i++) {
            Trash trash = createRandomTrash(i);
            if (trash != null) {
                trash.updatePosition();             // Mise à jour de la position du déchet
                initialTrashset.add(trash);         // Ajout du déchet à la liste
            }
        }

        // Enregistrement des déchets initiaux dans la base de données
        for (Trash trash : initialTrashset) {
            trashDAO.create(trash.convertTrashToTrashDB());
        }
    }

    // Initialisation des déchets locaux à partir de la base de données
    public void initLocalTrashes() {
        List<TrashDB> dataBaseTrashset = trashDAO.findAll();            // Récupération des déchets de la base de données
        localTrashset = new ArrayList<>();                              // Liste pour les déchets locaux

        // Création des objets Trash en fonction des données de la base
        for (TrashDB trash : dataBaseTrashset) {
            double x = trash.getX();
            double y = trash.getY();
            localTrashset.add(createTrashFromDB(trash, x, y));
        }
    }
    // Initialisation des monstres locaux à partir de la base de données
    public void initLocalMobs() {
        List<MobDB> mobList = mobDAO.findAll();            // Récupération des déchets de la base de données
        localMobs = new ArrayList<>();                              // Liste pour les monstres locaux

        // Création des objets Trash en fonction des données de la base
        for (MobDB mobDB : mobList) {
            double x = mobDB.getX();
            double y = mobDB.getY();
            localMobs.add(createMobFromDB(mobDB,x,y));
        }
    }

    // Création d'un déchet à partir des données de la base
    private Trash createTrashFromDB(TrashDB trashDB, double x, double y) {
        switch (trashDB.getName()) {
            case "Boat":
                return new Boat(x, y);
            case "Bottle":
                return new Bottle(x, y);
            case "Can":
                return new Can(x, y);
            case "OilContainer":
                return new OilContainer(x, y);
            case "PlasticBag":
                return new PlasticBag(x, y);
            case "Tire":
                return new Tire(x, y);
            default:
                return null;
        }
    }
    // Création d'un mob à partir des données de la base
    private Mob createMobFromDB(MobDB mobDB, double x,double y) {
        switch (mobDB.getName()) {
            case "Shark":
                return new Shark(x);
            default:
                return null;
        }
    }

    // Création d'un déchet aléatoire en fonction de l'index
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

    // Sons
    public void makeTrashSound() {
        new Thread(() -> {
            AudioFormat format = SoundManager.readWavFile("sounds/trash_sound.wav");
            double[] echantillons = SoundManager.readWAVFileSample("sounds/trash_sound.wav");
            assert format != null;
            SoundManager.playSound(echantillons, format.getSampleRate(), -25.0f);
        }).start();
    }

    public void playAmbientMusic() {
        new Thread(() -> {
            AudioFormat format = SoundManager.readWavFile("sounds/Ambient_music.wav");
            double[] echantillons = SoundManager.readWAVFileSample("sounds/Ambient_music.wav");
            assert format != null;
            SoundManager.playSound(echantillons, format.getSampleRate(), -15.0f);
        }).start();
    }

    // Getters et Setters
    public Avatar getMyAvatar() {
        return this.myAvatar;
    }

    public DiverDAO getDiverDAO() {
        return diverDAO;
    }

    public List<DiverDB> getAllDivers() {
        return allDiverDB;
    }

    public DiverDB getDiver() {
        return diverDB;
    }

    public void setDiver(DiverDB diverDB) {
        this.diverDB = diverDB;
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

    public void setAllDivers(List<DiverDB> allDiverDBS) {
        this.allDiverDB = allDiverDBS;
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