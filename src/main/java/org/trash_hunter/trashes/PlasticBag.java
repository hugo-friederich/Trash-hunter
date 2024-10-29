package org.trash_hunter.trashes;

import org.trash_hunter.tools.Couple;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class PlasticBag extends Trash {
    public PlasticBag (double x,double y){
        super(x, y);
        try {
            super.sprite = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("plastic_bag40x40.png")));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement de l'image : " + e.getMessage(), e);
        }
        width=super.sprite.getWidth();
        height=super.sprite.getHeight();
        nbPoints= 5;
        name="Plastic Bag";
        recupTime = 2000;       //temps de récupération en ms
        appearanceRangeY = new Couple(300,700-this.height);
    }
    public PlasticBag (){this(0,0);}
}
