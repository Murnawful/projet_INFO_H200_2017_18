package Moving;

import Model.Activable;
import Model.Deletable;
import Model.Directable;
import Model.Game;
import Objects.*;

import java.util.ArrayList;

public abstract class Character extends GameObject implements Directable, Deletable {

    protected int life;
    protected int direction;
    protected int force;
    protected ArrayList<InventoryObject> inventory;
    protected int sizeMaxInventory;
    protected int maxLife;
    protected Game game;

    ////////////////////////////////////////////////////////////////////////////////////////<Constructor>

    public Character(int X, int Y, int life, int maxLife, int force, ArrayList<InventoryObject> inventory, int sizeMaxInventory, int color, Game game) {
        super(X, Y, color);
        this.life = life;
        this.maxLife = maxLife;
        this.force = force;
        this.inventory = inventory;
        this.sizeMaxInventory = sizeMaxInventory;
        this.game = game;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<movingMethods>

    synchronized public void moveCharacter(int x, int y) {
        int nextX = this.getPosX() + x;
        int nextY = this.getPosY() + y;

        boolean obstacle = false;
        for (GameObject object : game.getGameObjects()) {
            if (object.isAtPosition(nextX, nextY)) {
                obstacle = object.isObstacle();
            }
            if (obstacle) {
                break;
            }
        }
        this.rotate(x, y);
        if (! obstacle) {
            move(x, y);
        }
        //teste si on quitte la map
        if (nextX == (game.getSize()) || nextY == (game.getSize()) ){
            System.out.println("map quitte");
        }
        game.notifyView();
    }

    public void move(int X, int Y) {
        this.posX = this.posX + X;
        this.posY = this.posY + Y;
    }

    public void rotate(int x, int y) {
        if(x == 0 && y == -1)
            direction = NORTH;
        else if(x == 0 && y == 1)
            direction = SOUTH;
        else if(x == 1 && y == 0)
            direction = EAST;
        else if(x == -1 && y == 0)
            direction = WEST;
    }

    ////////////////////////////////////////////////////////////////////////////////////////<diverseMethods>

    @Override
    public boolean isObstacle() {
        return true;
    }

    public void modifyLife(int change) {
        if(getLife() + change <= 0){
            if(this instanceof Monster){
                notifyDeletableObserver();
            }else {
                // Delete all
                game.gameOver();
            }
        }else if (getLife() + change <= getMaxLife()) {
            setLife(getLife() + change);
        }else if(getLife() + change == getMaxLife()){
            setLife(getMaxLife());
            System.out.println("Points de vie au maximum !");
        }
    }

    public void action() {
        Activable aimedObject = null;
        for(GameObject object : game.getGameObjects()){
            if(object.isAtPosition(getFrontX(),getFrontY())){
                aimedObject =  object;
            }
        }
        if(aimedObject != null){
            if(aimedObject instanceof InventoryObject){
                pickUp(aimedObject);
            }else if(aimedObject instanceof Chest){
                Chest chest = (Chest) aimedObject;
                int dir = chest.getDirection();
                if((dir == NORTH && direction == SOUTH) || (dir == SOUTH && direction == NORTH) || (dir == EAST && direction == WEST) || (dir == WEST && direction == EAST)){
                    chest.activate(0);
                }
            }
        }

    }

    private void pickUp(Activable aimedObject){
        if(inventory.size() < sizeMaxInventory){
            setInventory((InventoryObject) aimedObject);
            ((InventoryObject) aimedObject).setInInventory();
            aimedObject.activate(0);
            game.notifyView();
        }else{
            System.out.println("Inventaire plein !");
        }
    }

    public void attack(){
        GameObject o = null;
        Player p = game.getPlayer();
        for(GameObject object : game.getGameObjects()){
            if(object.isAtPosition(getFrontX(),getFrontY())){
                o =  object;
                break;
            }
        }
        if (o instanceof Character){
            Character aimedObject = (Character) o;
            aimedObject.modifyLife(-force);
        }else if(o instanceof BlockBreakable){
            BlockBreakable aimedObject = (BlockBreakable) o;
            aimedObject.activate(p.getForce());
            game.notifyView();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////<setMethods>

    public void setLife(int life){
        this.life = life;
    }

    public void setInventory(InventoryObject object){
        inventory.add(object);
    }

    ////////////////////////////////////////////////////////////////////////////////////////<getMethods>

    @Override
    public int getDirection() {
        return direction;
    }

    public int getFrontX() {
        int delta = 0;
        if (direction % 2 == 0){
            delta += 1 - direction;
        }
        return this.posX + delta;
    }

    public int getFrontY() {
        int delta = 0;
        if (direction % 2 != 0){
            delta += direction - 2;
        }
        return this.posY + delta;
    }

    public int getLife(){
        return life;
    }

    public int getForce(){
        return force;
    }

    public int getSizeInventory(){
        return inventory.size();
    }

    public int getSizeMaxInventory(){
        return sizeMaxInventory;
    }

    public int getMaxLife(){
        return maxLife;
    }

    public ArrayList<InventoryObject> getInventory(){
        return inventory;
    }
}
