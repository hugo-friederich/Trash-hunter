package org.trash_hunter.trashes;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class PlasticBag extends Trash {
    public PlasticBag (double x,double y){
        super(x, y);
        super.nbPoints= 5;
        super.name="Plastic Bag";
        super.time = 2000;       //temps de récupération en ms
        try {
            super.sprite = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("plastic_bag40x40.png")));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement de l'image : " + e.getMessage(), e);
        }
        super.width=super.sprite.getWidth();
        super.height=super.sprite.getHeight();
    }
}
