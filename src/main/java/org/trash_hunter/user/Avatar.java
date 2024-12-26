package org.trash_hunter.user;

import org.trash_hunter.util.DataTransferObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Objet permettant d'avoir la représentation de chaques joueurs
 */
public class Avatar implements DataTransferObject {
    private long id;                                                    // Identifiant unique
    private BufferedImage sprite;                                       // représentation du plongeur
    private BufferedImage leftFrame1, leftFrame2, leftFrame3, leftFrame4, rightFrame1, rightFrame2, rightFrame3, rightFrame4;  // Images du plongeur animé
    public int spriteCounter = 0;                                       // Permet de régler la vitesse d'animation
    public int spriteNum = 1;                                           // Numéro du sprite
    private float x, y;                                                 // Coordonnées
    private String pseudo;                                              // Pseudo plongeur
    private int score;                                                  // Score actuel
    private List<Integer> scoreHistory = new ArrayList<>();            // Historique des scores
    private int score_max;                                              // Score maximum
    private int speed;                                                  // Vitesse de déplacement (px/s)
    private int life;                                                   // Vie du plongeur
    private double oxygen;                                              // Oxygène du plongeur
    private int width, height;                                          // Largeur et hauteur image
    private boolean left, right, up, down;                              // Données de déplacement
    private String color;                                               // Couleur sélectionnée
    private Date creation_date;                                         // Date de création
    private Time game_time;                                             // Horaire création


    public Avatar(String pseudo, String color) {
        this.pseudo = pseudo;
        this.color = color;
        setDefaultValues();
        loadSprites();
        affectColorToAvatar(color);
    }

    private void setDefaultValues() {
        this.id = 0;       // L'identifiant est auto-incrémenté
        this.x = 720;      // Coordonnées du "spawn" = (720,390)
        this.y = 390;
        this.speed = 15;
        this.life = 3;     // Nombre de vie au départ
        this.oxygen = 100; // % d'oxygène
        this.score = 0;
        this.score_max = 0;
        this.creation_date = new Date(0);
        this.game_time = new Time(0);
        this.left = false;
        this.right = false;
        this.up = false;
        this.down = false;
        this.width = 50;
        this.height = 35;
        //this.direction = Direction.RIGHT; // Utilisation de l'énumération
    }

    public void loadSprites() {
        try {
            leftFrame1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("animatedDiver/left1.png")));
            leftFrame2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("animatedDiver/left2.png")));
            leftFrame3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("animatedDiver/left3.png")));
            leftFrame4 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("animatedDiver/left4.png")));
            rightFrame1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("animatedDiver/right1.png")));
            rightFrame2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("animatedDiver/right2.png")));
            rightFrame3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("animatedDiver/right3.png")));
            rightFrame4 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("animatedDiver/right4.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DiverDB convertAvatarToDiver() {
        DiverDB diverDB = new DiverDB();
        diverDB.setColor(this.color);
        diverDB.setPseudo(this.pseudo);
        diverDB.setScore(this.score);
        diverDB.setId(this.id);
        diverDB.setY(this.y);
        diverDB.setX(this.x);
        diverDB.setScore_max(this.score_max);
        diverDB.setGame_time(this.game_time);
        diverDB.setCreation_date(this.creation_date);
        diverDB.setOxygen(this.oxygen);
        return diverDB;
    }

    public void update() {
        if (left||right||up||down) {
            if (this.left) {
                this.sprite=leftFrame1;
                x -= this.speed;
                oxygen = Math.max(0, oxygen - 0.5);
                //direction = Direction.LEFT;
            }
            if (this.right) {
                this.sprite=rightFrame1;
                x += this.speed;
                oxygen = Math.max(0, oxygen - 0.5);
                //direction = Direction.RIGHT;
            }
            if (this.down) {
                y += this.speed;
                oxygen = Math.max(0, oxygen - 0.5);
                //direction = Direction.DOWN;
            }
            if (this.up) {
                y -= this.speed;
                oxygen = Math.max(0, oxygen - 0.5);
                //direction = Direction.UP;
            }
            if (this.y >= 0 && this.y < 25) {
                oxygen = Math.min(100, oxygen + 10);       //recharger l'oxygen jusqu'à 100%
            }
            /*
            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 3;
                } else if (spriteNum == 3) {
                    spriteNum = 4;
                } else if (spriteNum == 4) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }

             */
        }
    }
    /*
    private void updateSprite(){
            Boolean direction
            switch (direction) {
                case LEFT:
                    if(spriteNum==1) {
                        sprite = leftFrame1;
                    }
                    if(spriteNum==2){
                        sprite = leftFrame2;
                    }
                    if(spriteNum==3){
                        sprite = leftFrame3;
                    }
                    if(spriteNum==4){
                        sprite = leftFrame4;
                    }
                    break;
                case RIGHT:
                    if(spriteNum==1) {
                        sprite = rightFrame1;
                    }
                    if(spriteNum==2){
                        sprite = rightFrame2;
                    }
                    if(spriteNum==3){
                        sprite = rightFrame3;
                    }
                    if(spriteNum==4){
                        sprite = rightFrame4;
                    }
                    break;
                case UP:
                    break;
                case DOWN:
                    break;
            }

    }
     */

    public void rendering(Graphics2D contexte) {
        contexte.drawImage(this.sprite, (int) x, (int) y, null);
        drawOxygenBar(contexte);
    }

    public void updateScoreHistory() {
        if (score >score_max ) {
            scoreHistory.add(score);
        }
        if (scoreHistory.size() > 15) {
            scoreHistory.remove(0); // Utilisez remove(0) pour retirer le premier élément
        }
    }

    public void affectColorToAvatar(String colorSelected) {
        if (!colorSelected.equals("Animated")) {
            String coloredSprite = switch (colorSelected) {
                case "Blue" -> "plongeur_bleu.png";
                case "Red" -> "plongeur_rouge.png";
                case "Yellow" -> "plongeur_jaune.png";
                case "Green" -> "plongeur_vert.png";
                default -> null;
            };
            try {
                this.sprite = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(coloredSprite)));
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors du chargement de l'image : " + e.getMessage(), e);
            }
        } else {
            this.sprite = rightFrame1; // Par défaut, utilisez right1 pour l'animation
        }
    }

    private void drawOxygenBar(Graphics2D contexte) {
        int barX = (int) x;
        int barY = (int) y - 10;
        int barWidth = 40;
        int barHeight = 3;
        int oxygenWidth = (int) (barWidth * (this.oxygen / 100.0));

        contexte.setColor(Color.GRAY);
        contexte.fillRect(barX, barY, barWidth, barHeight);
        contexte.setColor(Color.GREEN);
        contexte.fillRect(barX, barY, oxygenWidth, barHeight);
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

    public List<Integer> getScoreHistory() {
        return scoreHistory;
    }

    public void setScoreHistory(ArrayList<Integer> scoreHistory) {
        this.scoreHistory = scoreHistory;
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

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public double getOxygen() {
        return oxygen;
    }

    public void setOxygen(double oxygen) {
        this.oxygen = oxygen;
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
