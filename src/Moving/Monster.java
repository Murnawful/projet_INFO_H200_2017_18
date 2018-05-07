package Moving;

import Model.Game;
import Objects.InventoryObject;

import java.util.ArrayList;

public class Monster extends Character implements Runnable{

    private int speed;
    private int viewRange;
    boolean threadActivate = true;
    private Player player;

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public Monster(int X, int Y, int life, int maxLife, int force, int speed, int viewRange, ArrayList<InventoryObject> inventory, int sizeMaxInventory, int color, Game game) {
        super(X, Y, life, maxLife, force, inventory, sizeMaxInventory, color, game);
        this.speed = speed;
        this.viewRange = viewRange;
        player = game.getPlayer();
    }

    ////////////////////////////////////////////////////////////////////////////////////////<diverseMethods>

    @Override
    public void run(){
        int step = 0;
        while(threadActivate){
            try {
                if(playerInViewRange()){
                    attack();
                    Thread.sleep(speed);
                    goTo(player.getPosX(),player.getPosY());
                    Thread.sleep(speed);
                }
                else{
                    makeARound(step);
                    Thread.sleep(speed);
                    step++;
                    step = step%8;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void goTo(int posX,int posY){ // goes to given location
        if(posX < this.getPosX()){
            moveCharacter(-1,0);
        }
        else if(posX > this.getPosX()){
            moveCharacter(1,0);
        }
        else if(posY < this.getPosY()){
            moveCharacter(0,-1);
        }
        else if(posY > this.getPosY()){
            moveCharacter(0,1);
        }
    }

    private void makeARound(int step){
        switch(step){
            case 0: //go left
                moveCharacter(-1,0);
                break;
            case 1: //go down
                moveCharacter(0,1);
                break;
            case 2: //go down
                moveCharacter(0,1);
                break;
            case 3: //go right
                moveCharacter(1,0);
                break;
            case 4: //go right
                moveCharacter(1,0);
                break;
            case 5: //go up
                moveCharacter(0,-1);
                break;
            case 6: //go up
                moveCharacter(0,-1);
                break;
            case 7:
                moveCharacter(-1,0);
                break;
        }
    }

    private boolean playerInViewRange() {
        boolean spotted=false;
        int deltaX = Math.abs(this.getPosX() - player.getPosX());
        int deltaY = Math.abs(this.getPosY() - player.getPosY());
        if(deltaX + deltaY <= this.viewRange){
            spotted = true;
        }
        return spotted;
    }

    public void stopThread(){
        threadActivate = false;
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
