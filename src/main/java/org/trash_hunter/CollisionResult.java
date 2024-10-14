package org.trash_hunter;

public class CollisionResult {
    public boolean collision;
    public int index;
    public int timeCollect;

    public CollisionResult(boolean collision, int index, int timeCollect) {
        this.collision = collision;
        this.index = index;
        this.timeCollect=timeCollect;
    }
    public CollisionResult(boolean collision, int index) {
        this.collision = collision;
        this.index = index;
        this.timeCollect=0;
    }
    public boolean getCollision(){
        return(this.collision);
    }
    public int getIndex(){
        return(this.index);
    }
}
