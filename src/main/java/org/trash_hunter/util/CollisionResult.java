package org.trash_hunter.util;

public class CollisionResult {
    public boolean collision;
    public int id;
    public int timeCollect;

    public CollisionResult(boolean collision, int index, int timeCollect) {
        this.collision = collision;
        this.id = index;
        this.timeCollect=timeCollect;
    }
    public CollisionResult(boolean collision, int index) {
        this.collision = collision;
        this.id = index;
        this.timeCollect=0;
    }
    public boolean getCollision(){
        return(this.collision);
    }
    public int getId(){
        return(this.id);
    }
}
