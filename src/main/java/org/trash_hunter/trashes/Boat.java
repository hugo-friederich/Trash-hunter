package org.trash_hunter.trashes;

import org.trash_hunter.tools.Couple;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;


public class Boat extends Trash{
    public Boat(double x,double y){
        super(x, y);
        super.nbPoints = 10;
        super.name = "Boat";
        super.recupTime = 5000;        //temps de récupération en ms
        try {
            super.sprite = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("boat148x80.png")));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement de l'image : " + e.getMessage(), e);
        }
        super.width=super.sprite.getWidth();
        super.height=super.sprite.getHeight();
        super.appearanceRangeY = new Couple(700-this.height,700-this.height);
    }
    public Boat (){
        this(0,0);
    }
}
