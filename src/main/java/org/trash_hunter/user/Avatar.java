package org.trash_hunter.user;

import org.trash_hunter.util.DataTransferObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Objects;


public class Avatar implements DataTransferObject {
    private BufferedImage sprite;                                       //image du plongeur
    private float x, y;                                                 //coordonées
    private String pseudo;                                              //pseudo plongueur
    private int score;                                                  //score actuel
    private ArrayList<Integer> scoreHistory = new ArrayList<>();        //historique des scores
    private int score_max;                                               //score maximum
    private int width, height;                                          //largeur et hauteur image
    private int speed;                                                  //vitesse de déplacement (px/s)
    private boolean left, right, up, down;                              //données de déplacement
    private long id;                                                    //identifiant unique
    private String color;                                               //couleur sélectionné
    private Date creation_date;                                                  //date de création
    private Time game_time;                                             //horaire création

    public Avatar(String pseudo, String color) {
        this.id = 0;    // l'identifiant est auto-incrémenté
        this.x = 720;   // coordonées du "spawn" = (720,390)
        this.y = 390;
        this.speed = 15;
        this.pseudo = pseudo;
        score = 0;
        score_max = 0;
        this.color = color;
        this.creation_date = new Date(0);
        this.game_time = new Time(0);
        this.left = false;
        this.right = false;
        this.up = false;
        this.down = false;
        affectColorToAvatar(this.color);
        this.width=50;
        this.height=35;
    }

    //Définition du constructeur par défaut (pseudo : Bob, color : blue)
    public Avatar() {
        this("Bob","blue");
    }
    public Diver convertAvatarToDiver(){
        Diver diver = new Diver();
        diver.setColor(this.color);
        diver.setPseudo(this.pseudo);
        diver.setScore(this.score);
        diver.setId(this.id);
        diver.setY(this.y);
        diver.setX(this.x);
        diver.setScore_max(this.score_max);
        diver.setGame_time(this.game_time);
        diver.setCreation_date(this.creation_date);
        return(diver);
    }
    public void update(){
        if (this.left) {x -= this.speed;}
        if (this.right) {x += this.speed;}
        if (this.down) {y += this.speed;}
        if (this.up) {y -= this.speed;}
    }
    public void updateScoreHistory(){
        if (score> score_max){
            scoreHistory.add(score);
        }
        if (scoreHistory.size()>15){
            scoreHistory.removeFirst();
        }
    }
    public void affectColorToAvatar(String colorSelected){
        String coloredSprite = "";
        if (colorSelected.equals("Blue")){
           coloredSprite="plongeur_bleu.png";
        }
        if (colorSelected.equals("Red")){
            coloredSprite="plongeur_rouge.png";
        }
        if (colorSelected.equals("Yellow")){
            coloredSprite="plongeur_jaune.png";
        }
        if (colorSelected.equals("Green")){
            coloredSprite="plongeur_vert.png";
        }
        try {
            this.sprite = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(coloredSprite)));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement de l'image : " + e.getMessage(), e);
        }
    }
    public void rendering (Graphics2D contexte){
        contexte.drawImage(this.sprite,(int)x,(int)y,null);
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ArrayList<Integer> getScoreHistory() {
        return scoreHistory;
    }

    public void setScoreHistory(ArrayList<Integer> scoreHistory) {
        this.scoreHistory = scoreHistory;
    }

    public int getScore_max() {
        return score_max;
    }

    public void setScore_max(int score_max) {
        this.score_max = score_max;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public Time getGame_time() {
        return game_time;
    }

    public void setGame_time(Time game_time) {
        this.game_time = game_time;
    }

    @Override
    public long getId() {
        return this.id;
    }

    public String toString() {
        return "Avatar{" +
                ", id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", pseudo='" + pseudo + '\'' +
                ", score=" + score +
                ", scoreMax=" + score_max +
                ", color='" + color + '\'' +
                ", date=" + creation_date +
                ", game_time=" + game_time +
                '}';
    }
}
