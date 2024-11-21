package org.trash_hunter.trashes;

import org.trash_hunter.util.Couple;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;


public class Boat extends Trash{
    public Boat(double x,double y){
        super(x, y);
        try {
            super.sprite = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("boat148x80.png")));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement de l'image : " + e.getMessage(), e);
        }
        width=super.sprite.getWidth();
        height=super.sprite.getHeight();
        nbPoints = 10;
        name = "Boat";
        repatriationTime = 5000;        //temps de récupération en ms
        appearanceRangeY = new Couple(780-this.height,780-this.height);
    }
    public Boat (){
        this(0,0);
        
    }
}
