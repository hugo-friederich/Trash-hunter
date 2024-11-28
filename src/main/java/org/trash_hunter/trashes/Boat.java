package org.trash_hunter.trashes;

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
        this.width=148;
        this.height=80;
        nbPoints = 10;
        name = "Boat";
        respawnTime = 5000;        //temps de réaparition en ms
        appearanceRangeYInf = 780-this.height;
        appearanceRangeYSup= 780-this.height;
    }
    public Boat (){
        this(0,0);
    }
    public Boat (long id){
        this(0,0);
        this.id=id;
    }
}
