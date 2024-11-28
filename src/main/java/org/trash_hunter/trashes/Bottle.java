package org.trash_hunter.trashes;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Bottle extends Trash {
    public Bottle(double x, double y){
        super(x, y);
        try {
            this.sprite = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("bottle9x25.png")));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement de l'image : " + e.getMessage(), e);
        }
        this.width=9;
        this.height=25;
        nbPoints= 1;
        name ="Bottle";
        respawnTime = 1;           //temps de réaparition quasi instantané
        appearanceRangeYInf = 0;
        appearanceRangeYSup= 700-this.height;
    }
    public Bottle (){this(0,0);}
    public Bottle (long id){
        this(0,0);
        this.id=id;
    }

}
