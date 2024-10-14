package org.trash_hunter.quadTree;

import org.trash_hunter.trashes.Trash;

import java.util.ArrayList;
import java.util.List;

public class QuadTree {
    private static final int MAX_OBJECTS = 4;
    private static final int MAX_LEVELS = 5;

    private int level;
    private List<Trash> objects;
    private Rectangle rectangle;
    private QuadTree[] quad;

    public QuadTree(int level, Rectangle bounds) {
        this.level = level;
        this.objects = new ArrayList<>();
        this.rectangle = bounds;                    //limites du quadrant
        this.quad = new QuadTree[4];            // 4 quadrans
    }

    public void split() {
        int newWidth = rectangle.getWidth()/2 ;
        int newHeight = rectangle.getHeight()/2 ;
        int x = rectangle.getX();
        int y = rectangle.getY();


    }

    public void insert(Trash trash) {
        // Insérer l'objet dans le bon quadrant
    }
/*
    public List<Trash> retrieve(List<Trash> returnObjects, Trash trash) {
        // Récupérer les objets potentiellement en collision
    }

 */
}
