package org.trash_hunter.trashes;

import org.trash_hunter.util.Couple;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OilContainer extends Trash {
    public OilContainer(double x, double y){
        super(x, y);
        try {
            super.sprite = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("oilcontainer40x60.png")));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement de l'image : " + e.getMessage(), e);
        }
        this.width=40;
        this.height=60;
        nbPoints = 10;
        name ="OilContainer";
        repatriationTime = 5000;       //temps de récupération en ms
        appearanceRangeYInf = 780-this.height;
        appearanceRangeYSup= 780-this.height;
    }
    public OilContainer(){this(0,0);}
    public OilContainer (long id){
        this(0,0);
        this.id=id;
    }
}
