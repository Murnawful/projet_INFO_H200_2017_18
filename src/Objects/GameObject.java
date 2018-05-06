package Objects;

import Model.Activable;
import Model.Deletable;
import Model.DeletableObserver;

import java.util.ArrayList;

public abstract class GameObject implements Activable, Deletable {

    protected int posX;
    protected int posY;
    protected int color;
    private ArrayList<DeletableObserver> observers = new ArrayList<DeletableObserver>();

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public GameObject(int X, int Y, int color) {
        this.posX = X;
        this.posY = Y;
        this.color = color;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<diverseMethods>

    public boolean isAtPosition(int x, int y) {
        return this.posX == x && this.posY == y;
    }

    synchronized public boolean isObstacle(){
        return false;
    }

    public void activate(int points){}

    @Override
    public void attachDeletable(DeletableObserver po) {
        observers.add(po);
    }

    @Override
    public void notifyDeletableObserver() {
        for (DeletableObserver o : observers) {
            o.delete(this);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////<setMethods>

    public void setPosX(int posX){
        this.posX = posX;
    }

    public void setPosY(int posY){
        this.posY = posY;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<getMethods>

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public int getColor() {
        return this.color;
    }
}