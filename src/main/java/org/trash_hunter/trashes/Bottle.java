package org.trash_hunter.trashes;

import org.trash_hunter.util.Couple;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Bottle extends Trash {
    public Bottle(double x, double y){
        super(x, y);
        try {
            super.sprite = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("bottle9x25.png")));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement de l'image : " + e.getMessage(), e);
        }
        width=super.sprite.getWidth();
        height=super.sprite.getHeight();
        nbPoints= 1;
        name ="Bottle";
        repatriationTime = 1;           //récupération quasi instantanée
        appearanceRangeYInf = 0;
        appearanceRangeYSup= 700-this.height;
    }
    public Bottle (){this(0,0);}


}
