package org.trash_hunter.mobs;


import org.trash_hunter.util.Direction;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Shark extends Mob {
    private BufferedImage leftShark, rightShark;
    public Shark(double x){
        super(x,600);               // Le requin est a une profondeur fixe -> seul x évolue
        name ="Shark";
        direction = Direction.LEFT;
        speed = 10;
        try {
            leftShark = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("shark_left.png")));
            rightShark = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("shark_right.png")));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement de l'image : " + e.getMessage(), e);
        }
        sprite=leftShark;
        width = sprite.getWidth();
        height = sprite.getHeight();
        nbPoints = 100;                               //retranche 100 points si il est touché
        nbLife = 2;                                  //retranche 2 vies si il est touché
    }

    /**
     * Le requin se déplace horizontalement à une certaine vitesse
     */
    public void update(){
        switch (direction){
            case LEFT :
                x=x-speed;
                sprite=leftShark;
                break;
            case RIGHT :
                x=x+speed;
                sprite=rightShark;
                break;
        }
    }

}
