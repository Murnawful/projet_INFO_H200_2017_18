package Moving;

import Model.Activable;
import Model.Deletable;
import Model.Game;
import Objects.InventoryObject;

import java.util.ArrayList;
import java.math.*;

public class Monster extends Character implements Runnable{

    private int speed;
    private int viewRange;
    private Player player;
    private Thread t;
    boolean threadActivate;

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public Monster(int X, int Y, int life, int maxLife, int strength, int speed, int viewRange, ArrayList<InventoryObject> inventory, int sizeMaxInventory, int color, Game game) {
        super(X, Y, life, maxLife, strength, inventory, sizeMaxInventory, color, game);
        this.speed = speed;
        this.viewRange = viewRange;
        player = game.getPlayer();
        threadActivate = true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<diverseMethods>

    @Override
    public void run(){
      int step = 0;
        while(threadActivate){
            try {
                if(playerInViewRange()){
                  attack();
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
    //fait un pas dans la direction indiquée
    private void goTo(int posX,int posY){
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
      //go left
      case 0:
        moveCharacter(-1,0);
        break;
      //go down
      case 1:
        moveCharacter(0,1);
        break;
      //go down
      case 2:
        moveCharacter(0,1);
        break;
      //go right
      case 3:
        moveCharacter(1,0);
        break;
      //go right
      case 4:
        moveCharacter(1,0);
        break;
      //go up
      case 5:
        moveCharacter(0,-1);
        break;
      //go up
      case 6:
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