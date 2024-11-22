package org.trash_hunter.util;

public class Couple {
    public int inf;
    public int sup;
    public int size;
    public Couple (int inf,int sup){
        this.inf=inf;
        this.sup=sup;
        this.size=sup-inf;
    }

    public int getInf() {
        return inf;
    }

    public void setInf(int inf) {
        this.inf = inf;
    }

    public int getSup() {
        return sup;
    }

    public void setSup(int sup) {
        this.sup = sup;
    }
}
