package org.trash_hunter;

import org.trash_hunter.util.DataTransferObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Objects;

public class Diver implements DataTransferObject {
    private BufferedImage sprite;
    private float x, y;
    private String pseudo;
    private int score;
    private ArrayList<Integer> scoreHistory = new ArrayList<>();
    private int scoreMax;
    private int width, height;
    private int speed;
    private boolean left, right, up, down;
    private long id;
    private String color;
    private Date date;
    private Time game_time;

    public Diver() {
        try {
            this.sprite = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("plongeur.png")));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement de l'image : " + e.getMessage(), e);
        }
        this.scoreMax=0;
        run();
    }
    public void update(){
        if (this.left) {x -= this.speed;}
        if (this.right) {x += this.speed;}
        if (this.down) {y += this.speed;}
        if (this.up) {y -= this.speed;}
    }
    public void updateScoreHistory(){
        if (score>scoreMax){
            scoreHistory.add(score);
        }
        if (scoreHistory.size()>15){
            scoreHistory.removeFirst();
        }
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

    public int getScoreMax() {
        return scoreMax;
    }

    public void setScoreMax(int scoreMax) {
        this.scoreMax = scoreMax;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getGame_time() {
        return game_time;
    }

    public void setGame_time(Time game_time) {
        this.game_time = game_time;
    }

    @Override
    public long getId() {
        return 0;
    }
}
