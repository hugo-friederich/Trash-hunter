package org.trash_hunter.trashes;

import org.trash_hunter.trashes.Trash;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Bottle extends Trash {
    public Bottle(int x, int y){
        super(x, y);
        super.nbPoints= 1;
        try {
            super.sprite = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("bouteille.jpg")));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement de l'image : " + e.getMessage(), e);
        }
    }
}
