package org.trash_hunter.trashes;

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
        this.width=40;
        this.height=40;
        nbPoints= 5;
        name="PlasticBag";
        respawnTime = 2000;       //temps de réaparition en ms
        appearanceRangeYInf = 300;
        appearanceRangeYSup= 700-this.height;
    }
    public PlasticBag (){this(0,0);}
    public PlasticBag (long id){
        this(0,0);
        this.id=id;
    }
}
