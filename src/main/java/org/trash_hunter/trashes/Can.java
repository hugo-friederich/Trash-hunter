package org.trash_hunter.trashes;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Can extends Trash {
    public Can(double x,double y){
        super(x, y);
        try {
            super.sprite = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("can12x25.png")));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement de l'image : " + e.getMessage(), e);
        }
        this.width=12;
        this.height=25;
        nbPoints= 1;
        name="Can";
        respawnTime = 1;          // réaparition quasi instantanée
        appearanceRangeYInf = 0;
        appearanceRangeYSup= 700-this.height;
    }
    public Can (){this(0,0);}
    public Can (long id){
        this(0,0);
        this.id=id;
    }
}
