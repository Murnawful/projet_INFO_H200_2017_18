package Moving;

import Model.Activable;
import Model.Deletable;
import Model.Game;
import Objects.InventoryObject;

import java.util.ArrayList;

public class Monster extends Character implements Runnable{

    private int speed;
    private int viewRange;

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public Monster(int X, int Y, int life, int maxLife, int force, int speed, int viewRange, ArrayList<InventoryObject> inventory, int sizeMaxInventory, int color, Game game) {
        super(X, Y, life, maxLife, force, inventory, sizeMaxInventory, color, game);
        this.speed = speed;
        this.viewRange = viewRange;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<diverseMethods>

    @Override
    public void run(){
        while(true){
            try {
                moveCharacter(0,-1);
                rotate(0,-1);
                Thread.sleep(speed);
                moveCharacter(-1,0);
                rotate(-1,0);
                Thread.sleep(speed);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void activate(int points) {

    }

    ////////////////////////////////////////////////////////////////////////////////////////<setMethods>

    public void setSpeed(int speed){
        this.speed = speed;
    }

    public void setViewRange(int viewRange){
        this.viewRange = viewRange;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<getMethods>

    public int getSpeed(){
        return speed;
    }

    public int getViewRange(){
        return viewRange;
    }
}