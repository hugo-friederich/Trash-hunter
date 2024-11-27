package org.trash_hunter;

import org.trash_hunter.trashes.*;
import org.trash_hunter.user.Avatar;
import org.trash_hunter.user.DiverDAO;
import org.trash_hunter.util.DatabaseConnection;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Game {
    private BufferedImage backgroundImage;
    final private Avatar myAvatar;
    private DiverDAO diverDAO;
    private TrashDAO trashDAO;
    private List <Avatar> allAvatars;
    private List <Trash> localTrashset;
    private final Random randomNbr;
    private int nbTrashes;
    public Game (String pseudo,String color) throws SQLException {

        //Création de l'objet permetant de génerer des nombres aléatoires
        this.randomNbr=new Random();

        //Chargement du fond
        try{
            this.backgroundImage= ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("fond_marin_1440x780.png")));
        }catch (IOException ex){
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE,null,ex);
        }

        //Creation du joueur ainsi que de l'instance lui permetant de communiquer à la BDD
        this.myAvatar = new Avatar(pseudo,color);
        this.diverDAO = new DiverDAO(DatabaseConnection.getConnection());
        this.diverDAO.create(myAvatar);

        //Initialisation des déchets
        this.nbTrashes = 30;
        this.trashDAO = new TrashDAO(DatabaseConnection.getConnection());
        this.trashDAO.clear();
        initLocalTrashes();
        initDBTrashes();
    }
    public Game() throws SQLException {
        this("Bob","Blue");
    }
    public void rendering(Graphics2D contexte){
        contexte.drawImage(this.backgroundImage,0,0,null);
        contexte.drawString("Score : "+ this.myAvatar.getScore(),10,20);
        for (Avatar otherDivers : allAvatars){
            otherDivers.rendering(contexte);
        }
        for (Trash trash : this.localTrashset) {
            trash.rendering(contexte);
        }
    }
    public void update() throws SQLException {
        //mise à jour du plongeur
        this.myAvatar.update();
        this.diverDAO.update(this.myAvatar,this.myAvatar.getId());
        this.allAvatars = diverDAO.findAll();

        //mise à jour du déchet dont la collision est True
        updateAfterCollisionDiverTrash();
        //Vérifie la collision avec les bords
        checkCollisionWithPanel();
    }
    public boolean isFinished() {return false;} //le jeu n'a pas de fin

    //Gestion des ollisions
    public void checkCollisionWithPanel(){
        if (myAvatar.getX() > backgroundImage.getWidth() - myAvatar.getWidth()) {
            myAvatar.setX(0);}  // collision avec le bord droit de la scene
        if (myAvatar.getX() < 0) {
            myAvatar.setX((float)backgroundImage.getWidth()-(float) myAvatar.getWidth());}  // collision avec le bord gauche de la scene
        if (myAvatar.getY() > backgroundImage.getHeight() - myAvatar.getHeight()) {
            myAvatar.setY((float)backgroundImage.getHeight()-(float) myAvatar.getWidth());}  // collision avec le bord bas de la scene
        if (myAvatar.getY() < 0) {
            myAvatar.setY(0);}  // collision avec le bord haut de la scene
    }

    /** Vérifie les collisions entre le plongeur et les déchets de la manière la plus simple.
     * Ainsi, pour chaque déchet dans l'ensemble, elle teste s'il y a une collision.
     * Si une collision est détectée, le déchet devient invisible et la méthode
     * renvoie un résultat de collision avec un indicateur de succès et l'index du déchet.
     * Si aucune collision n'est détectée, elle renvoie un résultat de collision avec
     * un indicateur d'échec et -1 comme index.
     * @return CollisionResult
     */
    public void updateAfterCollisionDiverTrash() {
        for (int id = 0; id < localTrashset.size(); id++) {
            Trash trash = localTrashset.get(id);
            if (isColliding(trash, myAvatar)) {
                this.myAvatar.setScore(this.myAvatar.getScore()+ localTrashset.get(id).getNbPoints());
                this.myAvatar.updateScoreHistory();
                trash.setVisible(0);
                Trash updatedTrash = localTrashset.get(id);
                updatedTrash.updatePosition();
                localTrashset.set(id,updatedTrash);
                trashDAO.update(updatedTrash,id);
            }
        }
    }

    private boolean isColliding(Trash trash, Avatar avatar) {
        return trash.getX() < avatar.getX() + avatar.getWidth() &&
                trash.getX() + trash.getWidth() > avatar.getX() &&
                trash.getY() < avatar.getY() + avatar.getHeight() &&
                trash.getY() + trash.getHeight() > avatar.getY();
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

    /**
     * Permet d'initialiser pour la première fois les déchets dans la base de
     * donnée.
     */
    public void initDBTrashes (){
        for (Trash trash : localTrashset){
            trashDAO.create(trash);
        }
    }
    //Getters and Setters
    public Avatar getDiver(){return this.myAvatar;}

    public DiverDAO getDiverDAO() {
        return diverDAO;
    }

    public List<Avatar> getAllDivers() {
        return allAvatars;
    }

    public List<Trash> getLocalTrashset() {
        return localTrashset;
    }

    public void setLocalTrashset(List<Trash> localTrashset) {
        this.localTrashset = localTrashset;
    }
}
