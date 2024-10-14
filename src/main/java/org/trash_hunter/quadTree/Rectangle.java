package org.trash_hunter.quadTree;

public class Rectangle {
    private int x ,y ,width, height;

    public Rectangle (int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /** Vérifie si un rectangle s'intersectionne avec un autre. Cette méthode est utile dans la classe QuadTree
     * pour partitionner les rectangle en sous rectangles si ils s'intersectent et ainsi réduire le complexité
     * de la recherche de collision.
     * @param other
     * @return boolean
     */
    public boolean intersect(Rectangle other){
        return (this.x< other.x + other.width &&
                this.x + this.width >other.x &&
                this.y<other.y + other.height &&
                this.y + this.height > other.y);
    }
    public boolean pointIsInRectangle (int pointX,int pointY){
        return(pointX <= this.x +this.width && pointX >= this.x &&
                pointY <= this.y +this.height && pointY >= this.y);
    }
}
