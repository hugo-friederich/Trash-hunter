package org.trash_hunter.trashes;

import org.trash_hunter.util.Couple;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Tire extends Trash{
    public Tire(double x,double y){
        super(x, y);
        try {
            super.sprite = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tire49x40.png")));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement de l'image : " + e.getMessage(), e);
        }
        width=super.sprite.getWidth();
        height=super.sprite.getHeight();
        nbPoints= 5;
        name="tire";
        reaparitionTime = 5000;       //temps de récupération en ms
        appearanceRangeY = new Couple(780-this.height,780-this.height);
    }
    public Tire (){this(0,0);}
}
