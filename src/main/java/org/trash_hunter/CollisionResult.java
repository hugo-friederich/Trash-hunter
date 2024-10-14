package org.trash_hunter;

public class CollisionResult {
    public boolean collision;
    public int index;

    public CollisionResult(boolean collision, int index) {
        this.collision = collision;
        this.index = index;
    }
    public boolean getCollision(){
        return(this.collision);
    }
    public int getIndex(){
        return(this.index);
    }
}
