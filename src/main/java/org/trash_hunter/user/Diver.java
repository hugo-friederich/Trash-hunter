package org.trash_hunter.user;

import org.trash_hunter.util.DataTransferObject;

import java.sql.Date;
import java.sql.Time;

public class Diver implements DataTransferObject {
    private long id;                                              //identifiant unique
    private float x, y;                                                 //coordonées
    private String pseudo;                                              //pseudo plongueur
    private int score;                                                  //score actuel
    private int score_max;                                              //score maximum
    private Date creation_date;                                         //date de création
    private Time game_time;                                             //horaire création
    private String color;                                               //couleur sélectionné

    public Diver(String pseudo, String color) {
        this.id = 0;                                                    // l'identifiant est auto-incrémenté
        this.x = 720;                                                   // coordonées du "spawn" = (720,390)
        this.y = 390;
        this.pseudo = pseudo;                                           //pseudo donnée par l'utilisateur
        score = 0;
        score_max = 0;
        this.creation_date = new Date(0);
        this.game_time = new Time(0);
        this.color = color;                                             //couleur donné par l'utilisateur
    }
    public Diver (){this("Bob","Red");}

    /**
     * Convertit un Diver en Avatar pour pouvoir après l'afficher
     * @return
     */
    public Avatar convertDiverToAvatar(){
        Avatar avatar = new Avatar(pseudo,color);
        avatar.setColor(this.color);
        avatar.setPseudo(this.pseudo);
        avatar.setScore(this.score);
        avatar.setId(this.id);
        avatar.setY(this.y);
        avatar.setX(this.x);
        avatar.setScore_max(this.score_max);
        avatar.setGame_time(this.game_time);
        avatar.setCreation_date(this.creation_date);
        return(avatar);
    }

    //Getters and setters
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

    public int getScore_max() {
        return score_max;
    }

    public void setScore_max(int score_max) {
        this.score_max = score_max;
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
    public void setId(long id) {
        this.id=id;
    }
    public String toString() {
        return "Diver{" +
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
