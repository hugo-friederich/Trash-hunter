package org.trash_hunter.trashes;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OilContainer extends Trash {
    public OilContainer(double x, double y){
        super(x, y);
        super.nbPoints = 10;
        super.name ="OilContainer";
        super.time = 5000;       //temps de récupération en ms
        try {
            super.sprite = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("oilcontainer40x60.png")));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement de l'image : " + e.getMessage(), e);
        }
        super.width=super.sprite.getWidth();
        super.height=super.sprite.getHeight();
    }
}
