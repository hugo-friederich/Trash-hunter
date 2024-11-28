package org.trash_hunter.trashes;

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
        this.width=49;
        this.height=40;
        nbPoints= 5;
        name="Tire";
        respawnTime = 5000;       //temps de réaparition en ms
        appearanceRangeYInf = 780-this.height;
        appearanceRangeYSup= 780-this.height;
    }
    public Tire (){this(0,0);}
    public Tire (long id){
        this(0,0);
        this.id=id;
    }
}
