package org.trash_hunter.trashes;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Boat extends Trash{
    public Boat(double x,double y){
        super(x, y);
        super.nbPoints = 10;
        super.name = "Boat";
        super.time = 5000;        //temps de récupération en ms
        try {
            super.sprite = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("boat148x80.png")));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement de l'image : " + e.getMessage(), e);
        }
        super.width=super.sprite.getWidth();
        super.height=super.sprite.getHeight();
    }
}
